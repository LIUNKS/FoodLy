import "@/styles/common.css"

export default function PedidoList() {
  const pedidos = [
    { id: 1, cliente: "Renzo", fecha: "hoy 10:28 PM", total: 55.0 },
    { id: 2, cliente: "Jhon", fecha: "hoy 10:34 PM", total: 63.0 },
    { id: 3, cliente: "Roxana", fecha: "hoy 10:10 PM", total: 48.0 },
    { id: 4, cliente: "Pedro", fecha: "hoy 10:50 PM", total: 55.0 },
    { id: 5, cliente: "carla", fecha: "hoy 10:55 PM", total: 63.0 },
    { id: 6, cliente: "Roxana", fecha: "hoy 10:58 PM", total: 48.0 },
  ]

  return (
    <div>
      <div className="table-responsive">
        <table className="table table-hover">
          <thead style={{ backgroundColor: 'var(--primary-light)' }}>
            <tr>
              <th scope="col" style={{ borderTopLeftRadius: '8px' }}>#</th>
              <th scope="col">Cliente</th>
              <th scope="col">Fecha</th>
              <th scope="col">Total</th>
              <th scope="col" style={{ borderTopRightRadius: '8px' }}>Acción</th>
            </tr>
          </thead>
          <tbody>
            {pedidos.map((pedido) => (
              <tr key={pedido.id}>
                <td>{pedido.id}</td>
                <td>
                  <i className="fas fa-user-circle me-1" style={{ color: 'var(--primary)' }}></i>
                  {pedido.cliente}
                </td>
                <td>
                  <i className="fas fa-clock me-1" style={{ color: 'var(--primary)' }}></i>
                  {pedido.fecha}
                </td>
                <td style={{ fontWeight: 500 }}>S/.{pedido.total.toFixed(2)}</td>
                <td>
                  <button className="btn btn-sm" style={{ color: 'var(--primary)', borderColor: 'var(--primary)', backgroundColor: 'var(--primary-light)' }}>
                    <i className="fas fa-edit"></i>
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <button className="foodly-btn w-100 mt-3">
        <i className="fas fa-sync-alt me-2"></i>
        Actualizar
      </button>
    </div>
  )
}
