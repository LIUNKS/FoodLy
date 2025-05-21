import MainLayout from "../../components/layout/MainLayout"
import CajaCard from "@/components/apertura-cierre/CajaCard"

export default function AperturaCierrePage() {
  return (
    <MainLayout>
      <nav className="navbar bg-body-tertiary">
        <div className="container-fluid">
          <h3>
            <i className="fas fa-box-open"></i> Apertura y Cierre Caja
          </h3>
          <a className="nav-link active mt-4" href="#">
            ADMIN
          </a>
        </div>
      </nav>

      <CajaCard abierta={false} />
    </MainLayout>
  )
}
