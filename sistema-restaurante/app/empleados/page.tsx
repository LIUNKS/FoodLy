import MainLayout from "@/components/layout/MainLayout"
import EmpleadosTable from "@/components/empleados/EmpleadosTable"
import ActionButton from "@/components/common/ActionButton"

export default function EmpleadosPage() {
  return (
    <MainLayout>
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2>Listado de Empleados</h2>
        <div>
          <ActionButton icon="fas fa-plus" variant="primary">
            Agregar
          </ActionButton>
          <ActionButton icon="fas fa-sync" variant="primary">
            Actualizar
          </ActionButton>
        </div>
      </div>

      <EmpleadosTable />

      <div className="d-flex justify-content-between align-items-center mt-3">
        <div>
          <span>Mostrando registros del 1 al 4 de un total de 4 registros</span>
        </div>
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
