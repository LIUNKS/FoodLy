import MainLayout from "@/components/layout/MainLayout"
import OrdenCard from "@/components/cocina/OrdenCard"
import PageHeader from "@/components/common/PageHeader"
import "@/styles/common.css"

export default function CocinaPage() {
  const ordenes = [
    {
      id: 1,
      cliente: "Juan Pérez",
      mesa: "Mesa 3",
      estado: "pendiente" as const,
      tiempo: "5 min",
      items: [
        { nombre: "Ceviche mixto", cantidad: 2 },
        { nombre: "Arroz con mariscos", cantidad: 1, observacion: "Sin ají" },
      ],
    },
    {
      id: 2,
      cliente: "María López",
      mesa: "Mesa 5",
      estado: "preparando" as const,
      tiempo: "10 min",
      items: [
        { nombre: "Lomo saltado", cantidad: 1 },
        { nombre: "Chicharrón de pescado", cantidad: 1 },
      ],
    },
    {
      id: 3,
      cliente: "Carlos Ruiz",
      mesa: "Mesa 2",
      estado: "listo" as const,
      tiempo: "15 min",
      items: [
        { nombre: "Ají de gallina", cantidad: 2 },
        { nombre: "Causa rellena", cantidad: 1 },
      ],
    },
  ]

  return (
    <MainLayout>
      <PageHeader title="Cocina" icon="fas fa-solid fa-utensils" />

      <div className="foodly-section fade-in-up">
        <div className="d-flex justify-content-between align-items-center mb-4">
          <h2 className="m-0" style={{ color: 'var(--primary)' }}>Gestión de Órdenes</h2>
          <div id="reloj" className="fs-4" style={{ color: 'var(--primary)', fontWeight: 500 }}>
            <i className="fas fa-clock me-2"></i>
            12:30:45
          </div>
        </div>

        <div className="row">
          {ordenes.map((orden) => (
            <div className="col-md-4 mb-4" key={orden.id}>
              <OrdenCard {...orden} />
            </div>
          ))}
        </div>
      </div>
    </MainLayout>
  )
}
