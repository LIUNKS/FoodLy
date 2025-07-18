"use client"
import ModalPortal from "../common/ModalPortal"
import type { Arqueo } from "./ArqueoTable"

// Props del modal de detalles de arqueo
interface Props {
  arqueo: Arqueo | null // Arqueo a mostrar
  open: boolean // Si el modal está abierto
  onClose: () => void // Handler para cerrar
}

/**
 * Modal que muestra los detalles de un arqueo de caja
 */
export default function ArqueoDetailModal({ arqueo, open, onClose }: Props) {
  // Si no está abierto o no hay arqueo, no renderiza nada
  if (!open || !arqueo) return null
  return (
    <ModalPortal>
      {/* Fondo oscuro del modal */}
      <div className="modal-backdrop fade show" style={{zIndex: 1040}} />
      {/* Contenedor principal del modal */}
      <div className="modal fade show d-block" tabIndex={-1} style={{zIndex: 1050}}>
        <div className="modal-dialog modal-dialog-centered">
          <div className="modal-content">
            {/* Header del modal */}
            <div className="modal-header bg-primary text-white">
              <h5 className="modal-title">
                <i className="fas fa-clipboard-check me-2"></i> Detalles del Arqueo
              </h5>
              <button type="button" className="btn-close btn-close-white" aria-label="Close" onClick={onClose}></button>
            </div>
            {/* Cuerpo del modal con los datos */}
            <div className="modal-body">
              <div><b>Fecha:</b> {arqueo.date}</div>
              <div><b>Hora Inicio:</b> {arqueo.star_time}</div>
              <div><b>Hora Fin:</b> {arqueo.end_time || 'Aún sin cierre'}</div>
              <div><b>Monto Inicial:</b> S/. {arqueo.init_amount?.toFixed(2)}</div>
              <div><b>Monto Final:</b> {arqueo.final_amount !== null ? `S/. ${arqueo.final_amount.toFixed(2)}` : 'Aún sin cierre'}</div>
              <div><b>Total:</b> {arqueo.total_amount !== null ? `S/. ${arqueo.total_amount.toFixed(2)}` : 'Aún sin cierre'}</div>
              <div><b>Caja:</b> {arqueo.box?.name_box || '-'}</div>
            </div>
            {/* Footer con botón de cerrar */}
            <div className="modal-footer">
              <button className="btn btn-secondary" onClick={onClose}>
                <i className="fas fa-times me-1"></i> Cerrar
              </button>
            </div>
          </div>
        </div>
      </div>
    </ModalPortal>
  )
}
