"use client"

import { useContext, useState } from "react"
import useProductoStock from "../hooks/useProductoStock"
import { ProductStock } from "../types/productoStok"
import {
  updateIncrementStockProduct,
  updateDecrementStockProduct
} from "../services/productStockService"
import DataTable from "@/components/common/DataTable"
import { toast, ToastContainer } from "react-toastify"
import Swal from "sweetalert2"
import withReactContent from "sweetalert2-react-content"
import "react-toastify/dist/ReactToastify.css"
import StockModalContent from "./modals/StockModalContent"
import { AuthContext } from "@/context/AuthContext"

const MySwal = withReactContent(Swal)

export default function InventarioTable() {
  // Obtenemos el token del usuario desde el contexto de autenticación
  const { token } = useContext(AuthContext)

  // Estados locales
  const [formData, setFormData] = useState<Partial<ProductStock>>({})
  const [cantidad, setCantidad] = useState<number>(0)
  const [observacion, setObservacion] = useState<string>("")
  const [modalType, setModalType] = useState<"increment" | "decrement">("increment")
  const [isModalOpen, setIsModalOpen] = useState(false)

  // Hook personalizado para obtener productos y gestionar estado de carga
  const { productos, loading, setProductos } = useProductoStock(token)

  /**
   * Abre el modal con los datos del producto y tipo de operación (entrada o salida)
   * @param producto - Objeto del producto seleccionado
   * @param type - Tipo de operación: "increment" o "decrement"
   */
  const openModal = (producto: ProductStock, type: "increment" | "decrement") => {
    setFormData(producto)
    setModalType(type)
    setCantidad(0)
    setObservacion("")
    setIsModalOpen(true)
  }

  /**
   * Maneja la lógica cuando el usuario hace clic en "Guardar" dentro del modal.
   * Actualiza el stock del producto en base a la operación realizada.
   * @param id_product_stock - ID del stock del producto
   * @param cantidad - Cantidad a modificar
   */
const handleModalSubmit = async ( cantidad: number) => {
  if (!token || !formData?.id_Stock) return
console.log("is stock:", formData?.id_Stock);
console.log("Cantidad ingresada:", cantidad);
  try {
    if (modalType === "increment") {
      await updateIncrementStockProduct(
        formData.id_Stock,
        cantidad,
        token
      )
      toast.success("Stock incrementado exitosamente.")
    } else {
      await updateDecrementStockProduct(
        formData.id_Stock,
        cantidad,
        token
      )
      toast.success("Stock decrementado exitosamente.")
    }

    setIsModalOpen(false)

    const updatedProductos = productos.map((prod) =>
      prod.product_stock.id_Stock === formData.id_Stock
        ? {
            ...prod,
            product_stock: {
              ...prod.product_stock,
              current_stock:
                prod.product_stock.current_stock +
                (modalType === "increment" ? cantidad : -cantidad),
            },
          }
        : prod
    )

    setProductos(updatedProductos)
  } catch (error) {
    console.error("Error actualizando stock:", error)
    toast.error("Error al actualizar el stock del producto.")
  }
}


  return (
    <div className="fade-in-up container-fluid">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2 className="fw-bold">Gestión Inventario Productos</h2>
      </div>

      {/* Tabla de productos con control de estados */}
      <DataTable
        headers={["#", "Producto", "Inventario Inicial", "Stock Actual", "Inventario Final", "Acciones"]}
        title="Listado de Productos"
      >
        {loading ? (
          <tr>
            <td colSpan={6} className="text-center">Cargando inventario de productos...</td>
          </tr>
        ) : productos.length === 0 ? (
          <tr>
            <td colSpan={6} className="text-center">No hay productos registrados en el inventario.</td>
          </tr>
        ) : (
          productos.map((p, i) => (
            <tr key={p.product_stock.id_Stock}>
              <td>{i + 1}</td>
              <td>{p.product_name}</td>
              <td>{p.product_stock.ini_stock}</td>
              <td>{p.product_stock.current_stock}</td>
              <td>{p.product_stock.total_sold}</td>
              <td>
                {/* Botones para modificar stock */}
                <button
                  className="btn btn-sm btn-outline-success me-2"
                  onClick={() => openModal(p, "increment")}
                >
                  <i className="fa-solid fa-plus"></i> Entrada
                </button>
                <button
                  className="btn btn-sm btn-outline-danger me-2"
                  onClick={() => openModal(p, "decrement")}
                >
                  <i className="fa-solid fa-minus"></i> Salida
                </button>
              </td>
            </tr>
          ))
        )}
      </DataTable>

      {/* Modal para aumentar o disminuir stock */}
      <StockModalContent
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        producto={formData}
        tipo={modalType}
        onSubmit={handleModalSubmit}
      />

      {/* Contenedor de notificaciones toast */}
      <ToastContainer position="top-right" autoClose={3000} />
    </div>
  )
}
