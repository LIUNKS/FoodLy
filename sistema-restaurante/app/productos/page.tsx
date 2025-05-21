// Importación de componentes necesarios para la página de empleados
import MainLayout from "@/components/layout/MainLayout"  // Componente de diseño principal que proporciona la estructura común
import ActionButton from "@/components/common/ActionButton"  // Botón reutilizable con iconos
import ProductosTable from "@/components/productos/ProductosTable" // Tabla que muestra la lista de productos
/**
 * Componente principal para la página de administración de productos
 * Muestra un listado de productos del restaurante con opciones para gestionar registros
 */
export default function ProductosPage() {
  return (
    <MainLayout>
      {/* Encabezado con título y botones de acción */}
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2>Listado de Productos</h2>
        <div>
          {/* Botón para agregar un nuevo empleado */}
          <ActionButton icon="fas fa-plus" variant="primary">
            Agregar
          </ActionButton>
          {/* Botón para actualizar la lista de productos */}
          <ActionButton icon="fas fa-sync" variant="primary">
            Actualizar
          </ActionButton>
        </div>
      </div>

      {/* Componente de tabla que muestra los productos registrados */}
      <ProductosTable />

      {/* Sección de paginación y estadísticas */}
      <div className="d-flex justify-content-between align-items-center mt-3">
        <div>
          <span>Mostrando registros del 1 al 4 de un total de 4 registros</span>
        </div>
        {/* Controles de paginación */}
        <nav aria-label="Page navigation">
          <ul className="pagination">
            <li className="page-item">
              <a className="page-link" href="#">
                Anterior
              </a>
            </li>
            <li className="page-item active">
              <a className="page-link" href="#">
                1
              </a>
            </li>
            <li className="page-item">
              <a className="page-link" href="#">
                Siguiente
              </a>
            </li>
          </ul>
        </nav>
      </div>
    </MainLayout>
  )
}
