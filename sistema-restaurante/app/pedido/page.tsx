// Importación de componentes necesarios para la página de pedidos
import MainLayout from "@/components/layout/MainLayout"        // Layout principal con estructura común
import PedidoForm from "@/components/pedido/PedidoForm"        // Formulario para crear/editar pedidos
import PedidoList from "@/components/pedido/PedidoList"        // Lista de pedidos recientes
import FilterDateRange from "@/components/common/FilterDateRange" // Filtro de rangos de fecha
import PageHeader from "@/components/common/PageHeader"        // Encabezado estándar para páginas
import "@/styles/common.css"                                  // Estilos comunes para mantener la consistencia visual

/**
 * Componente principal para la página de gestión de pedidos
 * Permite a los usuarios crear nuevos pedidos y ver los existentes
 */
export default function PedidoPage() {  return (
    <MainLayout>
      {/* Encabezado de página con título e icono */}
      <PageHeader title="Pedido" icon="fas fa-shopping-cart" />
      
      {/* Estructura de dos columnas: formulario y panel lateral */}
      <div className="row pedido-form g-3">
        {/* Columna principal con el formulario de pedido - primero en móvil, segundo en escritorio */}
        <div className="col-12 col-lg-8 order-2 order-lg-1">
          <PedidoForm />
        </div>

        {/* Columna lateral con filtros y pedidos recientes - primero en escritorio, segundo en móvil */}
        <div className="col-12 col-lg-4 order-1 order-lg-2 fade-in-up" style={{ animationDelay: "0.2s" }}>
          {/* Sección de filtros para búsqueda avanzada */}
          <div className="foodly-section mb-4">
            <h5 className="fs-5 text-primary fw-semibold mb-3">
              <i className="fas fa-filter me-2"></i>Filtros
            </h5>
            <FilterDateRange />
          </div>
          
          {/* Sección que muestra los pedidos recientes o filtrados */}
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
