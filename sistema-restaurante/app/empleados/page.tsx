// Importación de componentes necesarios para la página de empleados
import MainLayout from "@/components/layout/MainLayout"  // Componente de diseño principal que proporciona la estructura común
import EmpleadosTable from "@/components/empleados/EmpleadosTable"  // Tabla que muestra la lista de empleados
import ActionButton from "@/components/common/ActionButton"  // Botón reutilizable con iconos
import { ProtectedRoute } from "@/components/common/ProtectedRoute"  // Componente de protección de rutas
import { AppModule } from "@/utils/permissions"

/**
 * Componente principal para la página de administración de empleados
 * Muestra un listado de empleados del restaurante con opciones para gestionar registros
 * SOLO ACCESIBLE PARA ADMIN
 */
export default function EmpleadosPage() {
  return (
    <ProtectedRoute requiredModule={AppModule.EMPLEADOS}>
      <MainLayout>
        {/* Encabezado con título y botones de acción */}
        <div className="d-flex justify-content-between align-items-center mb-3">
          <h2>Listado de Empleados</h2>
          <div>
            {/* Botón para agregar un nuevo empleado */}
            <ActionButton icon="fas fa-plus" variant="primary">
              Agregar
            </ActionButton>
            {/* Botón para actualizar la lista de empleados */}
            <ActionButton icon="fas fa-sync" variant="primary">
              Actualizar
            </ActionButton>
          </div>
        </div>

        {/* Componente de tabla que muestra los empleados registrados */}
        <EmpleadosTable />      {/* Sección de paginación y estadísticas */}
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
    </ProtectedRoute>
  )
}
