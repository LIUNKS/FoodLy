"use client";

import { useState, useEffect } from "react";
import MainLayout from "../../components/layout/MainLayout";
import { boxService, BoxDTO } from "@/services/box-service";
import { useAuthData } from "@/hooks/useAuthData";
import { ProtectedRoute } from "@/components/common/ProtectedRoute";
import { atmService, ATMDTO } from "@/services/atm-service"; // Importar el servicio de ATM actualizado

function AperturaCierreAtmContent() {
    const { user } = useAuthData();
    const [cajas, setCajas] = useState<BoxDTO[]>([]);
    const [loading, setLoading] = useState(false);

    // Cargar las cajas al montar el componente
    // const loadCajas = async () => {
    //     setLoading(true);
    //     try {
    //         const response = await boxService.getBoxList();
    //         setCajas(response.data);
    //     } catch (error) {
    //         console.error("❌ Error al cargar las cajas:", error);
    //     } finally {
    //         setLoading(false);
    //     }
    // };

    // useEffect(() => {
    //     loadCajas();
    // }, []);

    // DEBUG: Log para verificar el estado del usuario
    useEffect(() => {
        console.log("🔍 AperturaCierreAtmContent DEBUG:");
        console.log("🔍 Usuario autenticado:", user);
        console.log("🔍 Rol de usuario:", user?.role);
   
    }, [user]);

    return (
        <MainLayout>
            <nav className="navbar bg-body-tertiary">
                <div className="container-fluid">
                    <h3>
                        <i className="fas fa-box-open"></i> Apertura y Cierre Caja
                    </h3>
                    <a className="nav-link active mt-4" href="#">
                        CAJA
                    </a>
                </div>
            </nav>

            <div className="container-fluid">
                <div className="row">
                    <div className="col-12">
                        <div
                            className="d-flex justify-content-between align-items-center mb-4 p-3 text-white"
                            style={{
                                background: 'linear-gradient(135deg, #ff6b35 0%, #f7931e 100%)',
                                borderRadius: '10px',
                                boxShadow: '0 4px 15px rgba(255, 107, 53, 0.3)'
                            }}
                        >
                            <div className="d-flex align-items-center">
                                <i className="fas fa-cash-register me-2" style={{ fontSize: '24px' }}></i>
                                <h3 className="mb-0">Lista de cajas</h3>
                            </div>
                            <div className="d-flex gap-2 align-items-center">
                                <button
                                    className="btn btn-light btn-sm"
                                    //onClick={loadCajas}
                                    disabled={loading}
                                    style={{ borderRadius: '8px' }}
                                    title="Sincronizar cajas, empleados y asignaciones"
                                >
                                    <i className="fas fa-sync-alt me-1"></i>
                                    {loading ? 'Sincronizando...' : 'Sincronizar'}
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

                {/* Aquí puedes agregar el componente que renderiza las cajas */}
                {/* Ejemplo: <CajaCardAtm cajas={cajas} loading={loading} /> */}
            </div>
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
