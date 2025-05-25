"use client"

import { ProductStock } from "../../types/productoStok"
import { useState, useEffect } from "react"
import ModalPortal from "@/components/common/ModalPortal"

interface StockModalContentProps {
  isOpen: boolean
  onClose: () => void
  producto: Partial<ProductStock> | null
  tipo: "increment" | "decrement"
  onSubmit: (cantidad: number) => void
}

/**
 * Modal para modificar el stock de un producto (entrada o salida).
 */
export default function StockModalContent({
  isOpen,
  onClose,
  producto,
  tipo,
  onSubmit,
}: StockModalContentProps) {
  const [cantidad, setCantidad] = useState<number>(0)

  useEffect(() => {
    if (isOpen) {
      setCantidad(0)
    }
  }, [isOpen])

  if (!isOpen || !producto) return null

  return (
    <ModalPortal>
      <div
        className="modal fade show"
        style={{ display: "block", backgroundColor: "rgba(0,0,0,0.5)" }}
        tabIndex={-1}
        role="dialog"
        aria-modal="true"
      >
        <div className="modal-dialog modal-dialog-centered">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title">
                {tipo === "increment" ? "Entrada de Stock" : "Salida de Stock"}
              </h5>
              <button type="button" className="btn-close" onClick={onClose} />
            </div>
            <div className="modal-body">
              <p><strong>Producto:</strong> {producto.product_name}</p>
              <p><strong>Cantidad actual:</strong> {producto.product_stock?.current_stock}</p>

              <div className="mb-3">
                <label className="form-label">Cantidad:</label>
           <input
              type="number"
              className="form-control"
              value={cantidad}
              onChange={(e) => setCantidad(Number(e.target.value))}
              min={0}
            />

              </div>

              <div className="mb-3">
                <label className="form-label">Observación:</label>
                <textarea
                  className="form-control"
                  rows={3} 
                />
              </div>
            </div>
            <div className="modal-footer">
              <button className="btn btn-secondary" onClick={onClose}>Cancelar</button>
              <button
                className="btn btn-primary"
                onClick={() => onSubmit(cantidad)}
              >
                Guardar
              </button>
            </div>
          </div>
        </div>
      </div>
    </ModalPortal>
  )
}
