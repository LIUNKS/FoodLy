import "@/styles/common.css"

export default function PedidoForm() {
  return (
    <div className="foodly-card fade-in-up">
      <div className="foodly-card-header">
        <button className="foodly-btn w-100">
          <i className="fas fa-check-circle me-2"></i>Finalizar Pedido
        </button>
      </div>
      <div className="foodly-card-body">
        <div className="mb-4">
          <label htmlFor="cliente" className="foodly-form-label">
            Cliente:
          </label>
          <div className="input-group">
            <span className="input-group-text" style={{ backgroundColor: 'var(--primary)', color: 'white' }}>
              <i className="fas fa-user"></i>
            </span>
            <input type="text" id="cliente" className="foodly-form-control" defaultValue="Jorge" />
          </div>
        </div>

        <ul className="nav nav-tabs" style={{ borderBottom: '2px solid var(--primary-light)' }}>
          <li className="nav-item">
            <a 
              className="nav-link active" 
              data-bs-toggle="tab" 
              href="#categorias"
              style={{ color: 'var(--primary)', fontWeight: 500 }}
            >
              <i className="fas fa-tags me-1"></i>Categorías
            </a>
          </li>
          <li className="nav-item">
            <a 
              className="nav-link" 
              data-bs-toggle="tab" 
              href="#productos"
              style={{ color: 'var(--text-medium)', fontWeight: 500 }}
            >
              <i className="fas fa-list me-1"></i>Productos
            </a>
          </li>
        </ul>

        <div className="tab-content mt-3">
          <div id="categorias" className="tab-pane fade show active">
            <label htmlFor="categorias-select" className="foodly-form-label">
              Categoría(s):
            </label>
            <div className="input-group">
              <span className="input-group-text" style={{ backgroundColor: 'var(--primary)', color: 'white' }}>
                <i className="fas fa-folder"></i>
              </span>
              <select className="foodly-form-control" id="categorias-select">
                <option value="">Seleccione una categoría...</option>
                <option value="ceviches">Ceviches</option>
                <option value="criollos">Criollos</option>
                <option value="fetucchini">Fetucchini</option>
                <option value="bebidas">Bebidas</option>
              </select>
            </div>
          </div>
          <div id="productos" className="tab-pane fade">
            <label htmlFor="productos-select" className="foodly-form-label">
              Producto(s):
            </label>
            <div className="input-group">
              <span className="input-group-text" style={{ backgroundColor: 'var(--primary)', color: 'white' }}>
                <i className="fas fa-utensils"></i>
              </span>
              <select className="foodly-form-control" id="productos-select">
                <option>Seleccione un producto...</option>
              </select>
            </div>
          </div>
        </div>

        <div className="mt-4 foodly-section">
          <h5 className="mb-3" style={{ color: 'var(--primary)', fontWeight: 600 }}>
            <i className="fas fa-shopping-cart me-2"></i>Detalle del Pedido
          </h5>
          <div className="table-responsive">
            <table className="table table-hover">
              <thead style={{ backgroundColor: 'var(--primary-light)' }}>
                <tr>
                  <th scope="col">Producto</th>
                  <th scope="col">Cantidad</th>
                  <th scope="col">Precio Unitario</th>
                  <th scope="col">Subtotal</th>
                  <th scope="col">Acciones</th>
                </tr>
              </thead>
              <tbody></tbody>
              <tfoot style={{ backgroundColor: 'var(--background)', fontWeight: 500 }}>
                <tr>
                  <td colSpan={3} className="text-end">
                    Subtotal
                  </td>
                  <td colSpan={2} className="text-start" id="subtotal">
                    S/.0.00
                  </td>
                </tr>
                <tr>
                  <td colSpan={3} className="text-end">
                    Impuesto (10%)
                  </td>
                  <td colSpan={2} className="text-start" id="impuesto">
                    S/.0.00
                  </td>
                </tr>
                <tr style={{ color: 'var(--primary)', fontWeight: 600 }}>
                  <td colSpan={3} className="text-end">
                    Total
                  </td>
                  <td colSpan={2} className="text-start" id="total">
                    S/.0.00
                  </td>
                </tr>
              </tfoot>
            </table>
          </div>
          <div className="d-flex justify-content-end mt-3">
            <button className="foodly-btn" style={{ backgroundColor: '#6c757d' }}>
              <i className="fas fa-trash-alt me-2"></i>Limpiar Carrito
            </button>
          </div>
        </div>
      </div>
    </div>
  )
}
