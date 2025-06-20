"use client";

import { useState, useEffect } from "react";
import MainLayout from "../../components/layout/MainLayout";
import { boxService, BoxDTO } from "@/services/box-service";
import { getAllEmpleados } from "@/app/empleados/services/empleadoService";
import { Empleado } from "@/app/empleados/types/empleado";
import { useAuthData } from "@/hooks/useAuthData";
import { ProtectedRoute } from "@/components/common/ProtectedRoute";

function AperturaCierreContent() {  const [cajas, setCajas] = useState<BoxDTO[]>([]);
  const [loading, setLoading] = useState(true);  const [empleados, setEmpleados] = useState<Empleado[]>([]);
  const [loadingEmpleados, setLoadingEmpleados] = useState(false);
  const [selectedCajaForATM, setSelectedCajaForATM] = useState<string | null>(null);  const { user, getStoredUserId, isAuthenticated, isAdmin } = useAuthData();

  // Cargar la lista de cajas al inicializar el componente
  const loadCajas = async () => {
    try {
      setLoading(true);
      console.log("🔄 Iniciando carga de cajas...");
      
      const response = await boxService.getBoxList();
      console.log("📦 Respuesta completa del servidor:", response);
      console.log("📦 Datos de cajas:", response.data);
      console.log("📦 Status de respuesta:", response.status);
      console.log("📦 Mensaje de respuesta:", response.message);
      
      // El boxService usa apiClient que devuelve SuccessMessage<BoxDTO[]>
      // Estructura: { status: number, message: string, data: BoxDTO[] }
      if (response && response.data && Array.isArray(response.data)) {
        setCajas(response.data);
        console.log("✅ Cajas establecidas en estado:", response.data);
        
        // Log adicional para verificar ATMs asignados
        response.data.forEach((caja, index) => {
          console.log(`📦 Caja ${index + 1}:`, {
            id: caja.id_box,
            nombre: caja.name_box,
            abierta: caja.is_open,
            atm: caja.atm ? {
              id: caja.atm.id_atm,
              nombre: caja.atm.name_atm,
              alias: caja.atm.alias
            } : null
          });
        });
        
      } else {
        console.warn("⚠️ response.data está vacío, es null, o no es un array");
        console.warn("⚠️ Estructura recibida:", response);
        setCajas([]);
      }
      
    } catch (error) {
      console.error("❌ Error al cargar las cajas:", error);
      setCajas([]);
    } finally {
      setLoading(false);
    }
  };// Cargar lista de empleados disponibles (ATMs)
  const loadATMs = async () => {
    try {
      setLoadingEmpleados(true);
      console.log("👥 Cargando lista de empleados...");
      
      // Obtener token del usuario usando la clave correcta
      const token = localStorage.getItem('authToken');
      if (!token) {
        console.error("❌ No se encontró token de autenticación");
        throw new Error('No se encontró token de autenticación');
      }
      
      console.log("🔑 Token encontrado, llamando a getAllEmpleados...");
      
      const response = await getAllEmpleados(token);
      console.log("📋 Respuesta completa de getAllEmpleados:", response);
      console.log("📋 response.data:", response.data);
      console.log("📋 response.status:", response.status);
      
      // Axios devuelve { data: T, status: number, ... }
      // Donde T puede ser directamente el array o un objeto con estructura { status, data }
      let empleadosData: Empleado[] = [];
      
      if (response.status === 200) {
        // Verificar si response.data es directamente un array
        if (Array.isArray(response.data)) {
          empleadosData = response.data;
          console.log("✅ response.data es directamente un array de empleados");
        } 
        // Verificar si response.data tiene la estructura { status, data: [] }
        else if (response.data && typeof response.data === 'object' && Array.isArray(response.data.data)) {
          empleadosData = response.data.data;
          console.log("✅ response.data tiene estructura { status, data: [] }");
        }        // Verificar si response.data tiene estructura de paginación { content: [] }
        else if (response.data && typeof response.data === 'object' && 'content' in response.data && Array.isArray((response.data as { content: Empleado[] }).content)) {
          empleadosData = (response.data as { content: Empleado[] }).content;
          console.log("✅ response.data tiene estructura de paginación { content: [] }");
        }
        else {
          console.warn("⚠️ Estructura de respuesta no reconocida");
          console.warn("⚠️ response.data:", response.data);
          console.warn("⚠️ Tipo de response.data:", typeof response.data);
          empleadosData = [];
        }
        
        setEmpleados(empleadosData);
        console.log("✅ Empleados cargados exitosamente:", empleadosData.length, "empleados");
      } else {
        console.warn("⚠️ Status de respuesta no es 200:", response.status);
        setEmpleados([]);
      }
      
    } catch (error) {
      console.error("❌ Error al cargar empleados:", error);
      if (error instanceof Error) {
        console.error("❌ Mensaje de error:", error.message);
      }
      setEmpleados([]);
      throw error;
    } finally {
      setLoadingEmpleados(false);
    }
  };  // Cargar cajas cuando el componente se monta
  useEffect(() => {
    console.log("🎬 Componente AperturaCierreContent montado, cargando cajas...");
    loadCajas();
  }, []);

  // Log para depurar cambios en el estado de cajas
  useEffect(() => {
    console.log("📊 Estado de cajas actualizado:", cajas);
    if (cajas.length > 0) {
      console.log("📊 Resumen de cajas:");
      cajas.forEach((caja, index) => {
        console.log(`  Caja ${index + 1}: ${caja.name_box} - ${caja.is_open ? 'Abierta' : 'Cerrada'} - ATM: ${caja.atm ? caja.atm.name_atm : 'Sin asignar'}`);
      });
    }
  }, [cajas]);

  // Recargar cajas cuando la ventana vuelve a tener foco (usuario regresa de otra pestaña)
  useEffect(() => {
    const handleFocus = () => {
      console.log("🔄 Ventana recuperó el foco, recargando cajas...");
      loadCajas();
    };

    const handleVisibilityChange = () => {
      if (!document.hidden) {
        console.log("🔄 Página se volvió visible, recargando cajas...");
        loadCajas();
      }
    };

    window.addEventListener('focus', handleFocus);
    document.addEventListener('visibilitychange', handleVisibilityChange);

    return () => {
      window.removeEventListener('focus', handleFocus);
      document.removeEventListener('visibilitychange', handleVisibilityChange);
    };
  }, []);const handleCreateCaja = async (nombreCaja: string) => {
    try {
      // Obtener el ID del usuario/admin
      const adminId = user?.id || getStoredUserId();
      
      if (!adminId) {
        console.error("❌ No se pudo obtener el ID del administrador");
        alert("Error: No se pudo identificar al usuario administrador");
        return;
      }

      console.log("🏗️ Creando caja:", { nombreCaja, adminId });
      
      // Llamar al servicio para crear la caja
      const response = await boxService.createBox(adminId, { name_box: nombreCaja });
      console.log("✅ Caja creada exitosamente:", response);
      
      // Recargar la lista de cajas desde el backend
      await loadCajas();
      
    } catch (error) {
      console.error("❌ Error al crear la caja:", error);
      alert("Error al crear la caja. Por favor, inténtelo de nuevo.");
    }
  };  // Función para abrir modal de asignar ATM
  const handleActivarCaja = async (cajaId: string) => {
    console.log("🔧 Abriendo modal para asignar ATM a caja:", cajaId);
    
    // Verificar si el usuario está autenticado
    if (!isAuthenticated()) {
      alert("⚠️ Debes iniciar sesión para asignar empleados a las cajas. Por favor, inicia sesión primero.");
      return;
    }
    
    console.log("🔐 Usuario autenticado, procediendo a cargar empleados...");
    setSelectedCajaForATM(cajaId);
    await loadATMs();
  };// Función para asignar empleado (ATM) a una caja
  const handleAsignarATM = async (atmId: string) => {
    if (!selectedCajaForATM) {
      console.error("❌ No hay caja seleccionada");
      alert("Error: No hay caja seleccionada");
      return;
    }

    try {
      console.log("🔗 Iniciando asignación de empleado a caja...");
      console.log("🔗 Caja seleccionada:", selectedCajaForATM);
      console.log("🔗 Empleado seleccionado:", atmId);
      
      // Encontrar el empleado seleccionado antes de la asignación
      const selectedEmpleado = empleados.find(emp => emp.id_atm === atmId);
      console.log("👤 Empleado encontrado:", selectedEmpleado);
      
      if (!selectedEmpleado) {
        console.error("❌ No se encontró el empleado en la lista local");
        alert("Error: No se encontró el empleado seleccionado");
        return;
      }      // ✨ NUEVA VALIDACIÓN: Verificar si el empleado tiene usuario
      if (!selectedEmpleado.user_atm) {
        console.warn("⚠️ El empleado no tiene usuario asociado");
        alert(`⚠️ El empleado "${selectedEmpleado.name_atm}" necesita tener un usuario asociado para poder ser asignado a una caja.\n\nPor favor, ve a la sección de Empleados y crea un usuario para este empleado primero.`);
        return;
      }

      console.log("✅ Empleado tiene usuario asociado:", selectedEmpleado.user_atm);
      
      console.log("📡 Llamando al servicio assignATMToBox...");
      
      // Llamar al servicio para asignar empleado a caja
      const response = await boxService.assignATMToBox(selectedCajaForATM, atmId);
      console.log("✅ Respuesta de asignación recibida:", response);
      console.log("✅ Status de respuesta:", response?.status);
      console.log("✅ Message de respuesta:", response?.message);
      console.log("✅ Data de respuesta:", response?.data);
      
      // Verificar si la asignación fue exitosa
      if (response && (response.status === 200 || response.status === 201)) {
        console.log("✅ Asignación exitosa, recargando cajas...");
        
        // Recargar las cajas desde el backend para obtener el estado más actual
        await loadCajas();
        
        console.log("✅ Empleado asignado exitosamente:", selectedEmpleado.name_atm);
        alert(`✅ Empleado ${selectedEmpleado.name_atm} asignado exitosamente a la caja`);
        
        // Cerrar el modal
        setSelectedCajaForATM(null);
      } else {
        console.error("❌ Respuesta inesperada del servidor:", response);
        alert("Error: La asignación no se completó correctamente");
      }
        } catch (error) {
      console.error("❌ Error completo al asignar empleado:", error);
      
      // Log detallado del error
      if (error instanceof Error) {
        console.error("❌ Error.name:", error.name);
        console.error("❌ Error.message:", error.message);
        console.error("❌ Error.stack:", error.stack);
      }
        // Si el error tiene una respuesta HTTP, mostrarla
      if (error && typeof error === 'object' && 'response' in error) {
        const httpError = error as { response: { status?: number; data?: unknown } };
        console.error("❌ HTTP Error Response:", httpError.response);
        console.error("❌ HTTP Status:", httpError.response?.status);
        console.error("❌ HTTP Data:", httpError.response?.data);
      }
      
      // Mostrar mensaje específico según el tipo de error
      if (error instanceof Error) {
        if (error.message.includes("atm necesita un user")) {
          alert("❌ Error: El empleado seleccionado necesita tener un usuario asociado para poder ser asignado a una caja.\n\nPor favor, ve a la sección de Empleados y crea un usuario para este empleado.");
        } else if (error.message.includes("401") || error.message.includes("403")) {
          alert("❌ Error de autenticación: Su sesión ha expirado. Por favor, inicie sesión nuevamente.");
        } else if (error.message.includes("404")) {
          alert("❌ Error: La caja o el empleado no fueron encontrados en el servidor.");
        } else if (error.message.includes("500")) {
          alert("❌ Error del servidor: Por favor, inténtelo de nuevo más tarde.");
        } else {
          alert(`❌ Error al asignar empleado: ${error.message}`);
        }
      } else {
        alert("❌ Error desconocido al asignar empleado. Por favor, inténtelo de nuevo.");
      }
    }
  };// Función para cambiar estado de caja
  const handleToggleCaja = async (cajaId: string) => {
    try {
      console.log("🔄 Cambiando estado de caja:", cajaId);
      
      // Encontrar la caja actual
      const caja = cajas.find(c => c.id_box === cajaId);
      if (!caja) {
        console.error("❌ Caja no encontrada");
        return;
      }
      
      // Llamar al backend para cambiar el estado
      // Aquí deberías implementar el endpoint correspondiente en el backend
      // const response = await boxService.toggleBoxState(cajaId, !caja.is_open);
      
      // Por ahora, actualizar localmente
      setCajas(prevCajas => 
        prevCajas.map(c => 
          c.id_box === cajaId 
            ? { ...c, is_open: !c.is_open }
            : c
        )
      );
      
      console.log("✅ Estado de caja actualizado");
      
    } catch (error) {
      console.error("❌ Error al cambiar estado de caja:", error);
      alert("Error al cambiar el estado de la caja");
    }
  };  return (
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
        <div className="row">
          <div className="col-12">
            {/* Header naranja estilo FoodLy */}
            <div className="d-flex justify-content-between align-items-center mb-4 p-3 text-white" 
                 style={{ 
                   background: 'linear-gradient(135deg, #ff6b35 0%, #f7931e 100%)',
                   borderRadius: '10px',
                   boxShadow: '0 4px 15px rgba(255, 107, 53, 0.3)'
                 }}>
              <h3 className="mb-0">
                <i className="fas fa-cash-register me-2"></i>
                Lista de cajas
              </h3>
              <div className="d-flex gap-2">
                <button 
                  className="btn btn-light btn-sm"
                  onClick={loadCajas}
                  disabled={loading}
                  style={{ borderRadius: '8px' }}
                >
                  <i className="fas fa-sync-alt me-1"></i>
                  {loading ? 'Actualizando...' : 'Actualizar'}
                </button>
              </div>
            </div>
          </div>
        </div>

        {/* Sección de cajas creadas */}
        <div className="row">
          <div className="col-12">
            <div className="d-flex justify-content-between align-items-center mb-4">
              <h5 className="mb-0 fw-bold text-dark">Cajas Creadas</h5>
              {isAdmin() && (
                <button 
                  type="button" 
                  className="btn text-white fw-semibold"
                  style={{ 
                    background: 'linear-gradient(135deg, #28a745 0%, #20c997 100%)',
                    border: 'none',
                    borderRadius: '8px',
                    padding: '8px 16px',
                    boxShadow: '0 2px 8px rgba(40, 167, 69, 0.3)'
                  }}
                  onClick={() => {
                    const nombre = prompt("Nombre de la nueva caja:");
                    if (nombre && nombre.trim()) {
                      handleCreateCaja(nombre.trim());
                    }
                  }}
                >
                  <i className="fas fa-plus me-2"></i>
                  Crear Caja
                </button>
              )}
            </div>
          </div>
        </div>

        {/* Grid de cajas */}
        <div className="row">
          {loading ? (
            <div className="col-12">
              <div className="text-center py-5">
                <div className="d-flex justify-content-center align-items-center">
                  <div className="spinner-border text-primary me-2" role="status">
                    <span className="visually-hidden">Cargando...</span>
                  </div>
                  <span className="text-muted">Cargando cajas...</span>
                </div>
              </div>
            </div>
          ) : cajas.length === 0 ? (
            <div className="col-12">
              <div className="text-center py-5">
                <div className="mb-4">
                  <i className="fas fa-box-open text-muted" style={{ fontSize: '60px' }}></i>
                </div>
                <p className="mb-3 text-muted" style={{ fontSize: '16px' }}>
                  {isAdmin() 
                    ? 'No hay cajas creadas. Haga click en "Crear Caja" para comenzar.'
                    : 'No hay cajas creadas. Solo los administradores pueden crear cajas.'
                  }
                </p>
                {isAdmin() && (
                  <button 
                    type="button" 
                    className="btn text-white fw-semibold"
                    style={{ 
                      background: 'linear-gradient(135deg, #28a745 0%, #20c997 100%)',
                      border: 'none',
                      borderRadius: '8px',
                      padding: '10px 20px'
                    }}
                    onClick={() => {
                      const nombre = prompt("Nombre de la nueva caja:");
                      if (nombre && nombre.trim()) {
                        handleCreateCaja(nombre.trim());
                      }
                    }}
                  >
                    <i className="fas fa-plus me-2"></i>
                    Crear Primera Caja
                  </button>
                )}
              </div>
            </div>
          ) : (
            cajas.map((caja) => {
              const formatDate = (dateString: string) => {
                try {
                  return new Date(dateString).toLocaleDateString('es-ES', {
                    day: '2-digit',
                    month: '2-digit',
                    year: 'numeric'
                  });
                } catch {
                  return dateString;
                }
              };

              return (
                <div key={caja.id_box} className="col-lg-4 col-md-6 col-12 mb-4">
                  <div 
                    className="card h-100 border-0 position-relative overflow-hidden"
                    style={{
                      borderRadius: '15px',
                      transition: 'all 0.3s cubic-bezier(0.4, 0, 0.2, 1)',
                      background: !caja.atm ? 
                        'linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%)' :
                        caja.atm && caja.is_open ? 
                        'linear-gradient(135deg, #d4edda 0%, #c3e6cb 100%)' :
                        'linear-gradient(135deg, #fff3cd 0%, #ffeaa7 100%)',
                      boxShadow: '0 4px 15px rgba(0, 0, 0, 0.1)',
                      minHeight: '200px'
                    }}
                    onMouseEnter={(e) => {
                      e.currentTarget.style.transform = 'translateY(-5px)';
                      e.currentTarget.style.boxShadow = '0 8px 25px rgba(0, 0, 0, 0.15)';
                    }}
                    onMouseLeave={(e) => {
                      e.currentTarget.style.transform = 'translateY(0)';
                      e.currentTarget.style.boxShadow = '0 4px 15px rgba(0, 0, 0, 0.1)';
                    }}
                  >
                    {/* Barra superior de color */}
                    <div 
                      style={{
                        height: '4px',
                        background: !caja.atm ? '#6c757d' :
                                  caja.atm && caja.is_open ? '#28a745' :
                                  '#ffc107',
                        width: '100%'
                      }}
                    />

                    <div className="card-body p-4 d-flex flex-column">
                      {/* Header con icono, nombre y badge */}
                      <div className="d-flex align-items-center justify-content-between mb-3">
                        <div className="d-flex align-items-center">
                          <div 
                            className="d-flex align-items-center justify-content-center me-3"
                            style={{
                              width: '45px',
                              height: '45px',
                              borderRadius: '12px',
                              background: !caja.atm ? '#6c757d' :
                                        caja.atm && caja.is_open ? '#28a745' :
                                        '#ffc107',
                              color: 'white'
                            }}
                          >
                            <i 
                              className={`fas ${
                                !caja.atm ? 'fa-lock' :
                                caja.atm && caja.is_open ? 'fa-play' :
                                'fa-pause'
                              }`}
                              style={{ fontSize: '18px' }}
                            />
                          </div>
                          <div>
                            <h6 className="mb-0 fw-bold" style={{ fontSize: '16px', color: '#2c3e50' }}>
                              {caja.name_box}
                            </h6>
                            <small className="text-muted" style={{ fontSize: '12px' }}>
                              {formatDate(caja.date)}
                            </small>
                          </div>
                        </div>
                        
                        {/* Badge de estado */}
                        <span 
                          className="badge rounded-pill px-3 py-1"
                          style={{
                            background: !caja.atm ? '#6c757d' :
                                      caja.atm && caja.is_open ? '#28a745' :
                                      '#ffc107',
                            color: 'white',
                            fontSize: '11px',
                            fontWeight: '600'
                          }}
                        >
                          {!caja.atm ? 'SIN ATM' :
                           caja.atm && caja.is_open ? 'ACTIVA' :
                           'PAUSADA'}
                        </span>
                      </div>

                      {/* Información del empleado */}
                      <div className="flex-grow-1 mb-3">
                        {caja.atm ? (
                          <div className="d-flex align-items-center">
                            <div 
                              className="d-flex align-items-center justify-content-center me-2"
                              style={{
                                width: '28px',
                                height: '28px',
                                borderRadius: '8px',
                                background: 'rgba(52, 152, 219, 0.1)',
                                color: '#3498db'
                              }}
                            >
                              <i className="fas fa-user" style={{ fontSize: '12px' }}/>
                            </div>
                            <div>
                              <p className="mb-0 fw-semibold" style={{ fontSize: '14px', color: '#2c3e50' }}>
                                {caja.atm.name_atm || caja.atm.alias || `ATM ${caja.atm.id_atm}`}
                              </p>
                              <small className="text-muted" style={{ fontSize: '11px' }}>Empleado asignado</small>
                            </div>
                          </div>
                        ) : (
                          <div className="text-center py-2">
                            <i className="fas fa-user-slash text-muted mb-2" style={{ fontSize: '24px' }}/>
                            <p className="mb-0 text-muted" style={{ fontSize: '12px', lineHeight: '1.4' }}>
                              Requiere empleado<br/>para funcionar
                            </p>
                          </div>
                        )}
                      </div>

                      {/* Botones de acción */}
                      <div className="mt-auto">
                        {!caja.atm ? (
                          isAdmin() ? (
                            <button 
                              className="btn w-100 fw-semibold text-white"
                              style={{ 
                                background: 'linear-gradient(135deg, #007bff 0%, #0056b3 100%)',
                                border: 'none',
                                borderRadius: '10px',
                                padding: '10px 12px',
                                fontSize: '13px',
                                transition: 'all 0.2s ease'
                              }}
                              onMouseEnter={(e) => {
                                e.currentTarget.style.background = 'linear-gradient(135deg, #0056b3 0%, #004085 100%)';
                                e.currentTarget.style.transform = 'translateY(-1px)';
                              }}
                              onMouseLeave={(e) => {
                                e.currentTarget.style.background = 'linear-gradient(135deg, #007bff 0%, #0056b3 100%)';
                                e.currentTarget.style.transform = 'translateY(0)';
                              }}
                              onClick={() => {
                                console.log("🔘 Asignar empleado:", caja.id_box);
                                handleActivarCaja(caja.id_box);
                              }}
                            >
                              <i className="fas fa-user-plus me-2"></i>
                              Asignar Empleado
                            </button>
                          ) : (
                            <div 
                              className="text-center py-3 px-3 rounded border"
                              style={{
                                background: 'rgba(108, 117, 125, 0.05)',
                                borderColor: '#dee2e6 !important',
                                borderStyle: 'dashed'
                              }}
                            >
                              <i className="fas fa-shield-alt text-muted mb-2" style={{ fontSize: '16px' }}></i>
                              <div>
                                <small className="text-muted d-block fw-semibold" style={{ fontSize: '11px' }}>
                                  ACCESO RESTRINGIDO
                                </small>
                                <small className="text-muted" style={{ fontSize: '10px' }}>
                                  Solo administradores pueden asignar empleados
                                </small>
                              </div>
                            </div>
                          )
                        ) : caja.is_open ? (
                          <button 
                            className="btn w-100 fw-semibold text-white"
                            style={{ 
                              background: 'linear-gradient(135deg, #ffc107 0%, #e0a800 100%)',
                              border: 'none',
                              borderRadius: '10px',
                              padding: '10px 12px',
                              fontSize: '13px'
                            }}
                            onClick={() => handleToggleCaja(caja.id_box)}
                          >
                            <i className="fas fa-pause me-2"></i>
                            Pausar
                          </button>
                        ) : (
                          <button 
                            className="btn w-100 fw-semibold text-white"
                            style={{ 
                              background: 'linear-gradient(135deg, #28a745 0%, #1e7e34 100%)',
                              border: 'none',
                              borderRadius: '10px',
                              padding: '10px 12px',
                              fontSize: '13px'
                            }}
                            onClick={() => handleToggleCaja(caja.id_box)}
                          >
                            <i className="fas fa-play me-2"></i>
                            Activar
                          </button>
                        )}
                      </div>
                    </div>
                  </div>
                </div>
              );
            })
          )}
        </div>
      </div>

      {/* Modal para asignar ATM */}
      {selectedCajaForATM && (
        <div className="modal fade show" style={{ display: 'block', backgroundColor: 'rgba(0,0,0,0.5)' }}>
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content">              <div className="modal-header">
                <h5 className="modal-title">
                  <i className="fas fa-user me-2"></i>
                  Asignar Empleado a Caja
                </h5>
                <button 
                  type="button" 
                  className="btn-close" 
                  onClick={() => setSelectedCajaForATM(null)}
                ></button>
              </div>              <div className="modal-body">
                <div className="mb-3">
                  <p className="text-muted">
                    Seleccione un empleado para asignar a la caja:
                  </p>
                  <div className="alert alert-info d-flex align-items-center">
                    <i className="fas fa-info-circle me-2"></i>
                    <small>
                      <strong>Nota:</strong> Solo los empleados que tienen un usuario asociado pueden ser asignados a una caja. 
                      Si un empleado no aparece disponible, debe crear un usuario para él en la sección de Empleados.
                    </small>
                  </div>
                </div>
                
                {loadingEmpleados ? (
                  <div className="text-center py-4">
                    <div className="spinner-border spinner-border-sm text-primary me-2" role="status">
                      <span className="visually-hidden">Cargando...</span>
                    </div>
                    Cargando empleados disponibles...
                  </div>                ) : empleados.length === 0 ? (
                  <div className="text-center py-4 text-muted">
                    <i className="fas fa-exclamation-triangle fa-2x mb-2 d-block text-warning"></i>
                    No hay empleados disponibles en este momento
                  </div>
                ) : (                  <div className="list-group">
                    {empleados.map((empleado) => {
                      const tieneUsuario = !!empleado.user_atm;
                      
                      return (
                        <div
                          key={empleado.id_atm}
                          className={`list-group-item d-flex justify-content-between align-items-center ${!tieneUsuario ? 'bg-light opacity-75' : ''}`}
                        >
                          <div className="flex-grow-1">
                            <div className="d-flex align-items-center">
                              <i className={`fas fa-user ${tieneUsuario ? 'text-primary' : 'text-muted'} me-2`}></i>
                              <div>
                                <h6 className={`mb-0 ${!tieneUsuario ? 'text-muted' : ''}`}>
                                  {empleado.name_atm}
                                  {tieneUsuario && (
                                    <span className="badge bg-success ms-2 small">
                                      <i className="fas fa-check-circle me-1"></i>
                                      Con Usuario
                                    </span>
                                  )}
                                  {!tieneUsuario && (
                                    <span className="badge bg-warning ms-2 small">
                                      <i className="fas fa-exclamation-triangle me-1"></i>
                                      Sin Usuario
                                    </span>
                                  )}
                                </h6>
                                <small className="text-muted">
                                  <i className="fas fa-at me-1"></i>
                                  {empleado.alias}
                                </small>
                                {empleado.email && (
                                  <div>
                                    <small className="text-muted">
                                      <i className="fas fa-envelope me-1"></i>
                                      {empleado.email}
                                    </small>
                                  </div>
                                )}
                                {!tieneUsuario && (
                                  <div>
                                    <small className="text-warning">
                                      <i className="fas fa-info-circle me-1"></i>
                                      Debe tener un usuario para ser asignado
                                    </small>
                                  </div>
                                )}
                              </div>
                            </div>
                          </div>
                          <div className="d-flex gap-2">
                            <button
                              type="button"
                              className={`btn btn-sm ${tieneUsuario ? 'btn-success' : 'btn-secondary'}`}
                              disabled={!tieneUsuario}
                              onClick={() => {
                                if (tieneUsuario) {
                                  console.log("🔘 Botón Asignar clickeado");
                                  console.log("🔘 empleado.id_atm:", empleado.id_atm);
                                  console.log("🔘 selectedCajaForATM:", selectedCajaForATM);
                                  console.log("🔘 empleado completo:", empleado);
                                  console.log("🔘 empleado.user_atm:", empleado.user_atm);
                                  handleAsignarATM(empleado.id_atm);
                                }
                              }}
                              title={tieneUsuario ? "Asignar empleado a la caja" : "El empleado necesita un usuario para ser asignado"}
                            >
                              <i className={`fas ${tieneUsuario ? 'fa-check' : 'fa-ban'} me-1`}></i>
                              {tieneUsuario ? 'Asignar' : 'No disponible'}
                            </button>
                          </div>
                        </div>
                      );
                    })}
                  </div>
                )}</div>
              <div className="modal-footer">
                <button 
                  type="button" 
                  className="btn btn-secondary"
                  onClick={() => setSelectedCajaForATM(null)}
                >
                  Cancelar
                </button>
              </div>
            </div>
          </div>        </div>
      )}
    </MainLayout>
  );
}

export default function AperturaCierrePage() {
  return (
    <ProtectedRoute requiredModule="/apertura-cierre">
      <AperturaCierreContent />
    </ProtectedRoute>
  );
}
