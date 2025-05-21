import "@/styles/common.css"

interface CajaCardProps {
  abierta?: boolean
}
  
export default function CajaCard({ abierta = false }: CajaCardProps) {
  return (
    <div className="foodly-card fade-in-up">
      <div className="foodly-card-header">
        <h3>{abierta ? "Caja Abierta" : "Caja Cerrada"}</h3>
      </div>
      <div className="foodly-card-body text-center">
        <div className="mb-4">
          <i className="fas fa-box-open" style={{ fontSize: '60px', color: 'var(--primary)' }}></i>
        </div>
        <p className="mb-3" style={{ fontSize: '16px', color: 'var(--text-medium)' }}>
          {abierta ? "Haga click en el botón para realizar el cierre de caja" : "Haga click en el botón para realizar la apertura de caja"}
        </p>
        <button type="button" className="foodly-btn">
          <i className={`fas ${abierta ? "fa-lock" : "fa-unlock"} me-2`}></i>
          {abierta ? "Cerrar Caja" : "Aperturar Caja"}
        </button>
      </div>
    </div>
  )
}
