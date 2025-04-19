import MainLayout from "@/components/layout/MainLayout"
import PedidoForm from "@/components/pedido/PedidoForm"
import PedidoList from "@/components/pedido/PedidoList"
import FilterDateRange from "@/components/common/FilterDateRange"
import PageHeader from "@/components/common/PageHeader"
import "@/styles/common.css"

export default function PedidoPage() {
  return (
    <MainLayout>
      <PageHeader title="Pedido" icon="fas fa-shopping-cart" />
      
      <div className="row pedido-form">
        <div className="col-md-8">
          <PedidoForm />
        </div>

        <div className="col-md-4 fade-in-up" style={{ animationDelay: "0.2s" }}>
          <div className="foodly-section mb-4">
            <h5 style={{ color: 'var(--primary)', fontWeight: 600, marginBottom: '1rem' }}>
              <i className="fas fa-filter me-2"></i>Filtros
            </h5>
            <FilterDateRange />
          </div>
          
          <div className="foodly-section">
            <h5 style={{ color: 'var(--primary)', fontWeight: 600, marginBottom: '1rem' }}>
              <i className="fas fa-clipboard-list me-2"></i>Pedidos Recientes
            </h5>
            <PedidoList />
          </div>
        </div>
      </div>
    </MainLayout>
  )
}
