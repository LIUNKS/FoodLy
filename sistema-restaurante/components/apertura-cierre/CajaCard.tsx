import "@/styles/common.css"
import { useState } from "react"
import { BoxDTO } from "@/services/box-service"

interface User {
  id: string;
  name: string;
  email: string;
  dni: string;
  role: string;
}

interface CajaCardProps {
  onCreateCaja?: (nombreCaja: string) => void
  cajas: BoxDTO[]
  loading: boolean
  onRefresh: () => void
  onActivarCaja?: (cajaId: string) => void
  onToggleCaja?: (cajaId: string) => void
  user: User | null
  isAdmin: boolean
}

export default function CajaCard({ onCreateCaja, cajas, loading, onRefresh, onActivarCaja, onToggleCaja, user, isAdmin }: CajaCardProps) {
  const [showModal, setShowModal] = useState(false)
  const [nombreCaja, setNombreCaja] = useState("")

  const handleCreateCaja = () => {
    if (nombreCaja.trim()) {
      onCreateCaja?.(nombreCaja.trim())
      setNombreCaja("")
      setShowModal(false)
    }
  }

  const formatDate = (dateString: string) => {
    try {
      return new Date(dateString).toLocaleDateString('es-ES', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
      });
    } catch {
      return dateString;
    }
  };

  // Ordenar cajas por fecha de creación (más recientes primero)
  const sortedCajas = [...cajas].sort((a, b) => {
    const dateA = new Date(a.date);
    const dateB = new Date(b.date);
    return dateB.getTime() - dateA.getTime();
  });

  return (
    <>
      <div className="foodly-card fade-in-up">
        <div className="foodly-card-header">
          <div className="d-flex justify-content-between align-items-center">
            <h3>Lista de cajas</h3>
            <button 
              className="btn btn-outline-light btn-sm"
              onClick={onRefresh}
              disabled={loading}
            >
              <i className="fas fa-sync-alt me-1"></i>
              {loading ? 'Actualizando...' : 'Actualizar'}
            </button>
          </div>
        </div>
        <div className="foodly-card-body">
          {/* Sección superior: Botón crear caja o mensaje */}
          {sortedCajas.length === 0 && !loading ? (
            <div className="text-center mb-4">
              <div className="mb-4">
                <i className="fas fa-box-open" style={{ fontSize: '60px', color: 'var(--primary)' }}></i>
              </div>
              <p className="mb-3" style={{ fontSize: '16px', color: 'var(--text-medium)' }}>
                {isAdmin 
                  ? 'Haga click en el botón para crear una nueva caja'
                  : 'No hay cajas creadas. Solo los administradores pueden crear cajas.'
                }
              </p>
              {isAdmin && (
                <button 
                  type="button" 
                  className="foodly-btn"
                  onClick={() => setShowModal(true)}
                >
                  <i className="fas fa-plus me-2"></i>
                  Crear Caja
                </button>
              )}
            </div>
          ) : (
            <div className="d-flex justify-content-between align-items-center mb-4">
              <h5 className="mb-0">Cajas Creadas</h5>
              {isAdmin && (
                <button 
                  type="button" 
                  className="foodly-btn"
                  onClick={() => setShowModal(true)}
                >
                  <i className="fas fa-plus me-2"></i>
                  Crear Caja
                </button>
              )}
            </div>
          )}

          {/* Grid de cajas rediseñado - Más limpio y moderno */}
          {(sortedCajas.length > 0 || loading) && (
            <div className="row">
              {loading ? (
                <div className="col-12">
                  <div className="text-center py-4">
                    <div className="d-flex justify-content-center align-items-center">
                      <div className="spinner-border spinner-border-sm text-primary me-2" role="status">
                        <span className="visually-hidden">Cargando...</span>
                      </div>
                      Cargando cajas...
                    </div>
                  </div>
                </div>
              ) : sortedCajas.length === 0 ? (
                <div className="col-12">
                  <div className="text-center py-4 text-muted">
                    <i className="fas fa-box-open fa-2x mb-2 d-block text-secondary"></i>
                    No hay cajas creadas aún
                  </div>
                </div>
              ) : (
                sortedCajas.map((caja) => (
                  <div key={caja.id_box} className="col-md-6 col-lg-4 mb-4">
                    <div 
                      className="card h-100 shadow-sm position-relative overflow-hidden"
                      style={{
                        borderRadius: '20px',
                        border: 'none',
                        transition: 'all 0.3s cubic-bezier(0.4, 0, 0.2, 1)',
                        height: '200px',
                        cursor: 'pointer',
                        background: !caja.atm ? 
                          'linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%)' :
                          caja.atm && caja.is_open ? 
                          'linear-gradient(135deg, #d4edda 0%, #c3e6cb 100%)' :
                          'linear-gradient(135deg, #fff3cd 0%, #ffeaa7 100%)'
                      }}
                      onMouseEnter={(e) => {
                        e.currentTarget.style.transform = 'translateY(-4px)';
                        e.currentTarget.style.boxShadow = '0 12px 40px rgba(0, 0, 0, 0.15)';
                      }}
                      onMouseLeave={(e) => {
                        e.currentTarget.style.transform = 'translateY(0)';
                        e.currentTarget.style.boxShadow = '0 4px 12px rgba(0, 0, 0, 0.1)';
                      }}
                    >
                      {/* Barra superior de color */}
                      <div 
                        style={{
                          height: '4px',
                          background: !caja.atm ? '#6c757d' :
                                    caja.atm && caja.is_open ? '#28a745' :
                                    '#ff6b35',
                          width: '100%'
                        }}
                      />

                      <div className="card-body p-4 d-flex flex-column h-100">
                        {/* Header con nombre y estado */}
                        <div className="d-flex align-items-center justify-content-between mb-3">
                          <div className="d-flex align-items-center">
                            <div 
                              className="d-flex align-items-center justify-content-center me-3"
                              style={{
                                width: '40px',
                                height: '40px',
                                borderRadius: '12px',
                                background: !caja.atm ? '#6c757d' :
                                          caja.atm && caja.is_open ? '#28a745' :
                                          '#ff6b35',
                                color: 'white'
                              }}
                            >
                              <i 
                                className={`fas ${
                                  !caja.atm ? 'fa-lock' :
                                  caja.atm && caja.is_open ? 'fa-play' :
                                  'fa-pause'
                                }`}
                                style={{ fontSize: '16px' }}
                              />
                            </div>
                            <div>
                              <h6 className="mb-0 fw-bold" style={{ fontSize: '15px', color: '#2c3e50' }}>
                                {caja.name_box}
                              </h6>
                              <small className="text-muted" style={{ fontSize: '11px' }}>
                                {formatDate(caja.date)}
                              </small>
                            </div>
                          </div>
                          
                          {/* Badge compacto */}
                          <span 
                            className="badge rounded-pill px-2 py-1"
                            style={{
                              background: !caja.atm ? '#6c757d' :
                                        caja.atm && caja.is_open ? '#28a745' :
                                        '#ff6b35',
                              color: 'white',
                              fontSize: '10px',
                              fontWeight: '600'
                            }}
                          >
                            {!caja.atm ? 'SIN ATM' :
                             caja.atm && caja.is_open ? 'ACTIVA' :
                             'PAUSADA'}
                          </span>
                        </div>

                        {/* Info del empleado o mensaje */}
                        <div className="flex-grow-1 mb-3">
                          {caja.atm ? (
                            <div className="d-flex align-items-center">
                              <div 
                                className="d-flex align-items-center justify-content-center me-2"
                                style={{
                                  width: '24px',
                                  height: '24px',
                                  borderRadius: '6px',
                                  background: 'rgba(52, 152, 219, 0.1)',
                                  color: '#3498db'
                                }}
                              >
                                <i className="fas fa-user" style={{ fontSize: '10px' }}/>
                              </div>
                              <div>
                                <p className="mb-0 fw-semibold" style={{ fontSize: '13px', color: '#2c3e50' }}>
                                  {caja.atm.name_atm || caja.atm.alias || `ATM ${caja.atm.id_atm}`}
                                </p>
                                <small className="text-muted" style={{ fontSize: '10px' }}>Empleado asignado</small>
                              </div>
                            </div>
                          ) : (
                            <div className="text-center">
                              <i className="fas fa-user-slash text-muted mb-1" style={{ fontSize: '20px' }}/>
                              <p className="mb-0 text-muted" style={{ fontSize: '11px' }}>
                                Requiere empleado<br/>para funcionar
                              </p>
                            </div>
                          )}
                        </div>

                        {/* Botón de acción compacto */}
                        <div className="mt-auto">
                          {!caja.atm ? (
                            // Solo mostrar botón "Asignar Empleado" si el usuario es admin
                            isAdmin ? (
                              <button 
                                className="btn w-100 fw-semibold"
                                style={{ 
                                  background: '#3498db',
                                  color: 'white',
                                  border: 'none',
                                  borderRadius: '10px',
                                  padding: '8px 12px',
                                  fontSize: '12px',
                                  transition: 'all 0.2s ease'
                                }}
                                onMouseEnter={(e) => {
                                  e.currentTarget.style.background = '#2980b9';
                                  e.currentTarget.style.transform = 'translateY(-1px)';
                                }}
                                onMouseLeave={(e) => {
                                  e.currentTarget.style.background = '#3498db';
                                  e.currentTarget.style.transform = 'translateY(0)';
                                }}
                                onClick={() => {
                                  console.log("🔘 Asignar empleado:", caja.id_box);
                                  onActivarCaja?.(caja.id_box);
                                }}
                              >
                                <i className="fas fa-user-plus me-2"></i>
                                Asignar Empleado
                              </button>
                            ) : (
                              // Mensaje mejorado para usuarios no admin
                              <div 
                                className="text-center py-2 px-3 rounded"
                                style={{
                                  background: 'rgba(108, 117, 125, 0.1)',
                                  border: '1px dashed #6c757d'
                                }}
                              >
                                <i className="fas fa-shield-alt text-muted mb-1" style={{ fontSize: '14px' }}></i>
                                <div>
                                  <small className="text-muted d-block" style={{ fontSize: '10px', fontWeight: '500' }}>
                                    ACCESO RESTRINGIDO
                                  </small>
                                  <small className="text-muted" style={{ fontSize: '9px' }}>
                                    Solo administradores
                                  </small>
                                </div>
                              </div>
                            )
                          ) : caja.is_open ? (
                            <button 
                              className="btn w-100 fw-semibold"
                              style={{ 
                                background: '#f39c12',
                                color: 'white',
                                border: 'none',
                                borderRadius: '10px',
                                padding: '8px 12px',
                                fontSize: '12px'
                              }}
                              onClick={() => onToggleCaja?.(caja.id_box)}
                            >
                              <i className="fas fa-pause me-2"></i>
                              Pausar
                            </button>
                          ) : (
                            <button 
                              className="btn w-100 fw-semibold"
                              style={{ 
                                background: '#27ae60',
                                color: 'white',
                                border: 'none',
                                borderRadius: '10px',
                                padding: '8px 12px',
                                fontSize: '12px'
                              }}
                              onClick={() => onToggleCaja?.(caja.id_box)}
                            >
                              <i className="fas fa-play me-2"></i>
                              Activar
                            </button>
                          )}
                        </div>
                      </div>
                    </div>
                  </div>
                ))
              )}
            </div>
          )}
        </div>
      </div>

      {/* Modal para crear caja */}
      {showModal && (
        <div className="modal fade show" style={{ display: 'block', backgroundColor: 'rgba(0,0,0,0.5)' }}>
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Crear Nueva Caja</h5>
                <button 
                  type="button" 
                  className="btn-close" 
                  onClick={() => {
                    setShowModal(false)
                    setNombreCaja("")
                  }}
                ></button>
              </div>
              <div className="modal-body">
                <div className="mb-3">
                  <label htmlFor="nombreCaja" className="form-label">Nombre de la caja</label>
                  <input
                    type="text"
                    className="form-control"
                    id="nombreCaja"
                    value={nombreCaja}
                    onChange={(e) => setNombreCaja(e.target.value)}
                    placeholder="Ingrese el nombre de la caja"
                    autoFocus
                  />
                </div>
              </div>
              <div className="modal-footer">
                <button 
                  type="button" 
                  className="btn btn-secondary"
                  onClick={() => {
                    setShowModal(false)
                    setNombreCaja("")
                  }}
                >
                  Cancelar
                </button>
                <button 
                  type="button" 
                  className="btn btn-primary"
                  onClick={handleCreateCaja}
                  disabled={!nombreCaja.trim()}
                >
                  Crear Caja
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  )
}
