"use client" // 👈 Necesario para usar useState y useEffect en Next.js app directory

// Importación del componente DataTable para mostrar datos tabulares
import DataTable from "../common/DataTable"
// Importación de estilos comunes para mantener la consistencia visual
import "@/styles/common.css"
import { useEffect, useState } from "react"
import axios from "axios"

/**
 * Componente que renderiza una tabla de productos con sus datos
 * Muestra información detallada de cada productos y opciones para editarlos
 */
export default function ProductosTable() {
  const [productos, setProductos] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/product/list")
      .then((response) => {
        if (response.data.status === "OK") {
          console.log("Productos recibidos:", response.data.data)
          setProductos(response.data.data)
        } else {
          console.error("Error al obtener productos:", response.data.message)
        }
      })
      .catch((error) => {
        console.error("Error en la solicitud:", error)
      })
      .finally(() => {
        setLoading(false)
      })
  }, [])

  return (
    <div className="fade-in-up">
      <DataTable
        headers={["#", "Nombre", "Categoría", "Precio", "Acción"]}
        title="Listado de Productos"
      >
        {loading ? (
          <tr>
            <td colSpan={5}>Cargando productos...</td>
          </tr>
        ) : productos.length === 0 ? (
          <tr>
            <td colSpan={5}>No hay productos disponibles.</td>
          </tr>
        ) : (
          productos.map((producto, index) => (
            <tr key={producto.id_Product}>
              <th scope="row">{index + 1}</th>
              <td>{producto.name_product}</td>
              <td>{producto.category}</td>
              <td>{producto.price}</td>
              <td>
                <button
                  className="foodly-btn-secondary"
                  style={{
                    padding: "5px 10px",
                    fontSize: "0.9rem",
                  }}
                >
                  <i className="fas fa-edit me-1"></i>Editar
                </button>
              </td>
            </tr>
          ))
        )}
      </DataTable>
    </div>
  )
}
