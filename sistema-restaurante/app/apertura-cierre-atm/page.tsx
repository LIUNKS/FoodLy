"use client";

import { useState, useEffect } from "react";
import MainLayout from "../../components/layout/MainLayout";
import { boxService, BoxDTO } from "@/services/box-service";
import { useAuthData } from "@/hooks/useAuthData";
import { ProtectedRoute } from "@/components/common/ProtectedRoute";
import { atmService, ATMDTO } from "@/services/atm-service"; // Importar el servicio de ATM actualizado
import CreateCajaModal from "@/components/apertura-cierre/CreateCajaModal";
import CajaCard from "@/components/apertura-cierre/CajaCard"; // Importar el componente CajaCard
import ArqueoModal, { ArqueoInitDTO } from "@/components/apertura-cierre/ArqueoModal"; // Importar el modal de arqueo
import ConfirmationModal from "@/components/common/ConfirmationModal"; // Modal de confirmación
import NotificationModal from "@/components/common/NotificationModal"; // Modal de notificación

function AperturaCierreAtmContent() {
  const [cajas, setCajas] = useState<BoxDTO[]>([]);
  const [loading, setLoading] = useState(true);
  const [atms, setAtms] = useState<ATMDTO[]>([]); // Estado para ATMs según filtro actual
  const [allAtms, setAllAtms] = useState<ATMDTO[]>([]); // Estado para todos los ATMs
  const [atmFilter, setAtmFilter] = useState<'all' | 'active' | 'inactive'>('active'); // Filtro de ATMs
  const [loadingAtms, setLoadingAtms] = useState(false); // Estado para carga de ATMs
  const [selectedCajaForATM, setSelectedCajaForATM] = useState<string | null>(null);
  const [showATMModal, setShowATMModal] = useState(false);
  const [isCreateCajaModalOpen, setIsCreateCajaModalOpen] = useState(false);
 
  const [showArqueoModal, setShowArqueoModal] = useState(false);
  const [selectedCajaForArqueo, setSelectedCajaForArqueo] = useState<string | null>(null);
  
  // Estados para modales de confirmación y notificación
  const [confirmationModal, setConfirmationModal] = useState<{
    isOpen: boolean;
    title: string;
    message: string;
    onConfirm: () => void;
    type?: 'danger' | 'warning' | 'info' | 'success';
    confirmText?: string;
    isLoading?: boolean;
  }>({
    isOpen: false,
    title: '',
    message: '',
    onConfirm: () => {},
  });
  
  const [notificationModal, setNotificationModal] = useState<{
    isOpen: boolean;
    title: string;
    message: string;
    type?: 'success' | 'error' | 'warning' | 'info';
  }>({
    isOpen: false,
    title: '',
    message: '',
  });

  // Funciones auxiliares para modales
  const showNotification = (
    title: string, 
    message: string, 
    type: 'success' | 'error' | 'warning' | 'info' = 'info'
  ) => {
    setNotificationModal({
      isOpen: true,
      title,
      message,
      type,
    });
  };

  const showConfirmation = (
    title: string,
    message: string,
    onConfirm: () => void,
    type: 'danger' | 'warning' | 'info' | 'success' = 'warning',
    confirmText: string = 'Confirmar'
  ) => {
    setConfirmationModal({
      isOpen: true,
      title,
      message,
      onConfirm,
      type,
      confirmText,
      isLoading: false,
    });
  };

  const closeConfirmationModal = () => {
    setConfirmationModal(prev => ({ ...prev, isOpen: false }));
  };

  const closeNotificationModal = () => {
    setNotificationModal(prev => ({ ...prev, isOpen: false }));
  };
  
  // Mapa global que almacena todas las cajas asignadas a ATMs
  // La clave es el ID de la caja y el valor es el ID del ATM asignado
  const [cajasAsignadas, setCajasAsignadas] = useState<Map<string, string>>(new Map());
  
  // Log para monitorear cambios en el mapa global
  useEffect(() => {
    console.log("🔄 Mapa global de cajas asignadas actualizado:");
    console.log("📊 Total de cajas asignadas:", cajasAsignadas.size);
    console.log("📊 Detalle:", Array.from(cajasAsignadas.entries()));
  }, [cajasAsignadas]);

  // Log para monitorear cambios en los estados de los modales
  useEffect(() => {
    console.log("🔄 Estados de modales:", {
      showATMModal,
      showArqueoModal,
      selectedCajaForATM,
      selectedCajaForArqueo
    });
  }, [showATMModal, showArqueoModal, selectedCajaForATM, selectedCajaForArqueo]);

  // Estado para controlar errores de conexión
  const [connectionError, setConnectionError] = useState<string | null>(null);

  // Función para mostrar error de conexión
  const showConnectionError = (message: string) => {
    setConnectionError(message);
    // Auto-ocultar después de 10 segundos
    setTimeout(() => setConnectionError(null), 10000);
  };

  // Función para verificar la conexión a internet
  const checkInternetConnection = () => {
    if (!navigator.onLine) {
      showConnectionError("No hay conexión a internet. Verifique su conectividad.");
      return false;
    }
    return true;
  };

  // Cargar la lista de cajas al inicializar el componente
  const loadCajas = async () => {
    try {
      setLoading(true);
      console.log("🔄 Iniciando carga de cajas...");
      
      // Verificar conexión a internet antes de la petición
      if (!navigator.onLine) {
        throw new Error("No hay conexión a internet. Verifique su conectividad.");
      }
      
      const response = await boxService.getBoxList();
      console.log("📦 Respuesta completa del servidor:", response);
      console.log("📦 Datos de cajas:", response.data);
      console.log("📦 Status de respuesta:", response.status);
      console.log("📦 Mensaje de respuesta:", response.message);
      
      // El boxService usa apiClient que devuelve SuccessMessage<BoxDTO[]>
      // Estructura: { status: number, message: string, data: BoxDTO[] }
      if (response && response.data && Array.isArray(response.data)) {
        console.log("🔄 Procesando datos de cajas del servidor y sincronizando con mapa global...");
        
        // Preservar el estado de carga (isLoading) durante actualizaciones para evitar parpadeos
        // y sincronizar con el mapa global de cajas asignadas
        const updatedCajas = response.data.map(newCaja => {
          const existingCaja = cajas.find(c => c.id_box === newCaja.id_box);
          const cajaId = newCaja.id_box;
          
          // Verificar si esta caja está en el mapa global de asignaciones
          const atmAsignado = cajasAsignadas.get(cajaId);
          
          // Si la caja ya tiene ATM en su propio objeto, usarlo
          // Si no, pero está en el mapa global, buscar el ATM correspondiente
          let atmInfo = newCaja.atm;
          
          if (!atmInfo && atmAsignado) {
            console.log(`🔄 Caja ${cajaId} tiene ATM ${atmAsignado} según el mapa global`);
            
            // Buscar el ATM en la lista de ATMs
            const atmEncontrado = allAtms.find(a => a.id_atm === atmAsignado);
            
            if (atmEncontrado) {
              console.log(`✅ ATM encontrado para caja ${cajaId}:`, atmEncontrado.name_atm);
              
              // Crear un objeto con la información necesaria del ATM
              atmInfo = {
                id_atm: atmEncontrado.id_atm,
                name_atm: atmEncontrado.name_atm,
                alias: atmEncontrado.alias,
                email: atmEncontrado.email,
                phone: atmEncontrado.phone,
                dni: atmEncontrado.dni
              };
            } else {
              console.log(`⚠️ ATM ${atmAsignado} no encontrado en la lista local`);
              
              // Aunque no tengamos los datos completos, creamos un objeto mínimo para indicar que está asignado
              atmInfo = {
                id_atm: atmAsignado
              };
            }
          }
          
          // Si la caja tiene un ATM según el servidor, pero no está en el mapa global,
          // actualizamos el mapa global (este caso es importante para la sincronización)
          if (newCaja.atm && !cajasAsignadas.has(cajaId)) {
            console.log(`🔄 Caja ${cajaId} tiene ATM ${newCaja.atm.id_atm} según el servidor, actualizando mapa global`);
            setCajasAsignadas(prevMapa => {
              const nuevoMapa = new Map(prevMapa);
              nuevoMapa.set(cajaId, newCaja.atm!.id_atm);
              return nuevoMapa;
            });
          }
          
          return {
            ...newCaja,
            isLoading: existingCaja?.isLoading || false,
            // Usar la información del ATM que determinamos arriba
            atm: atmInfo || existingCaja?.atm
          };
        });
        
        // Actualizar el estado de cajas con los datos sincronizados
        setCajas(updatedCajas);
        console.log("✅ Cajas establecidas en estado con sincronización de asignaciones:", updatedCajas);
        
        // Log adicional para verificar ATMs asignados
        updatedCajas.forEach((caja, index) => {
          console.log(`📦 Caja ${index + 1}:`, {
            id: caja.id_box,
            nombre: caja.name_box,
            abierta: caja.is_open,
            atm: caja.atm ? {
              id: caja.atm.id_atm,
              nombre: caja.atm.name_atm || 'Sin nombre',
              alias: caja.atm.alias || 'Sin alias'
            } : 'Sin ATM asignado'
          });
        });
        
        // Limpiar cualquier error de conexión previo
        setConnectionError(null);
      } else {
        console.warn("⚠️ response.data está vacío, es null, o no es un array");
        console.warn("⚠️ Estructura recibida:", response);
        
        // No borrar el estado de cajas actual si ya existía, para evitar parpadeos en caso de error
        if (cajas.length === 0) {
          setCajas([]);
        }
        
        showConnectionError("Error en el formato de respuesta del servidor al cargar cajas.");
      }
      
    } catch (error) {
      console.error("❌ Error al cargar las cajas:", error);
      
      // Mostrar mensaje de error en la UI
      let errorMessage = "Error al cargar las cajas.";
      if (error instanceof Error) {
        errorMessage += " " + error.message;
      }
      showConnectionError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  // Cargar lista de ATMs activos disponibles para asignación
  const loadActiveATMs = async () => {
    try {
      setLoadingAtms(true);
      console.log("🏧 Cargando lista de ATMs activos...");
      
      // Obtener solo los ATMs activos - con un timeout máximo para no bloquear la UI
      const activeATMsPromise = atmService.getActiveATMs();
      
      // Usar Promise.race para limitar el tiempo de espera a 3 segundos
      const timeoutPromise = new Promise<ATMDTO[]>((_, reject) => {
        setTimeout(() => reject(new Error("Timeout al cargar ATMs")), 3000);
      });
      
      const activeATMs = await Promise.race([activeATMsPromise, timeoutPromise])
        .catch(error => {
          console.warn("⚠️ Tiempo de espera excedido al cargar ATMs:", error);
          // Devolver un array vacío en caso de timeout para no bloquear la UI
          return [];
        });
      
      console.log("✅ ATMs activos obtenidos:", activeATMs.length);
      console.log("📋 Lista de ATMs activos:", activeATMs);
      
      setAtms(activeATMs);
      
    } catch (error) {
      console.error("❌ Error al cargar ATMs activos:", error);
      if (error instanceof Error) {
        console.error("❌ Mensaje de error:", error.message);
      }
      setAtms([]);
    } finally {
      setLoadingAtms(false);
    }
  };



  return (
    <MainLayout>
      <nav className="navbar bg-body-tertiary">
        <div className="container-fluid">
          <h3>
            <i className="fas fa-box-open"></i> Apertura y Cierre Caja
          </h3>
          <a className="nav-link active mt-4" href="#">
            ADMIN
          </a>        </div>
      </nav>      {/* Componente integrado para crear y listar cajas - Diseño mejorado */}
      <div className="container-fluid">
        {/* Mensaje de error de conexión */}
        {connectionError && (
          <div 
            className="alert alert-danger position-fixed bottom-0 start-50 translate-middle-x mb-4 d-flex align-items-center"
            style={{
              zIndex: 9999,
              boxShadow: '0 4px 12px rgba(0,0,0,0.15)',
              maxWidth: '90%',
              width: 'auto',
            }}
          >
            <i className="fas fa-exclamation-triangle me-2"></i>
            <div>{connectionError}</div>
            <button 
              type="button" 
              className="btn-close ms-3" 
              onClick={() => setConnectionError(null)}
            ></button>
          </div>
        )}

      </div>      {/* Modal para asignar ATM */}


      {/* Modal de Arqueo Inicial */}
      {showArqueoModal && selectedCajaForArqueo && (
        <ArqueoModal
          isOpen={showArqueoModal}
          onClose={() => {
            setShowArqueoModal(false);
            setSelectedCajaForArqueo(null);
          }}
          onSubmit={(arqueoData: ArqueoInitDTO) => {
          
          }}
          isLoading={cajas.find(c => c.id_box === selectedCajaForArqueo)?.isLoading || false}
          cajaNombre={cajas.find(c => c.id_box === selectedCajaForArqueo)?.name_box || 'Caja'}
        />
      )}

      {/* Modal para crear caja */}

      {/* Modal de confirmación */}
      <ConfirmationModal
        isOpen={confirmationModal.isOpen}
        title={confirmationModal.title}
        message={confirmationModal.message}
        onConfirm={() => {
          confirmationModal.onConfirm();
          closeConfirmationModal();
        }}
        onClose={closeConfirmationModal}
        type={confirmationModal.type}
        confirmText={confirmationModal.confirmText}
        isLoading={confirmationModal.isLoading}
      />

      {/* Modal de notificación */}
      <NotificationModal
        isOpen={notificationModal.isOpen}
        title={notificationModal.title}
        message={notificationModal.message}
        type={notificationModal.type || 'info'}
        onClose={closeNotificationModal}
      />
    </MainLayout>
  );
}

export default function AperturaCierreAtmPage() {
  return (
    <ProtectedRoute requiredModule="/apertura-cierre-atm">
      <AperturaCierreAtmContent />
    </ProtectedRoute>
  );
}
