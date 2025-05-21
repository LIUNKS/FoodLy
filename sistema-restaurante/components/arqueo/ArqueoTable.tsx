import DataTable from "../common/DataTable"

export default function ArqueoTable() {
  const arqueos = [
    {
      id: 1,
      fecha: "2024-10-16",
      inicio: "5:09 PM",
      egreso: { efectivo: 0, tarjeta: 0 },
      ingreso: { efectivo: 865.5, tarjeta: 748 },
      totalCaja: 1580.5,
      totalBruto: 1613.5,
      totalNeto: 1613.5,
    },
  ]

  return (
    <DataTable
      headers={[
        "#",
        "Fecha",
        "Inicio",
        "Egreso",
        "Ingreso",
        "Total en Caja",
        "Total en Bruto",
        "Total Neto",
        "Acciones",
      ]}
    >
      {arqueos.map((arqueo) => (
        <tr key={arqueo.id}>
          <td>{arqueo.id}</td>
          <td>{arqueo.fecha}</td>
          <td>{arqueo.inicio}</td>
          <td>
            Efectivo: {arqueo.egreso.efectivo.toFixed(2)}, Tarjeta: {arqueo.egreso.tarjeta.toFixed(2)}
          </td>
          <td>
            Efectivo: {arqueo.ingreso.efectivo.toFixed(2)}, Tarjeta: {arqueo.ingreso.tarjeta.toFixed(2)}
          </td>
          <td>{arqueo.totalCaja.toFixed(2)}</td>
          <td>{arqueo.totalBruto.toFixed(2)}</td>
          <td>{arqueo.totalNeto.toFixed(2)}</td>
          <td>
            <button className="btn btn-warning btn-sm">
              <i className="fas fa-edit"></i> Editar
            </button>
            <button className="btn btn-danger btn-sm">
              <i className="fas fa-trash"></i> Eliminar
            </button>
          </td>
        </tr>
      ))}
    </DataTable>
  )
}
