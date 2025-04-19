"use client"

import { useEffect } from "react"
import MainLayout from "@/components/layout/MainLayout"
import Chart from "chart.js/auto"
import "@/styles/logros.css"

export default function LogrosPage() {
  useEffect(() => {
    // Inicializar los gráficos cuando el componente se monta
    const initCharts = () => {
      // Gráfico de total de pedidos
      const ctxPedidos = document.getElementById('chartPedidos') as HTMLCanvasElement;
      if (ctxPedidos) {
        new Chart(ctxPedidos, {
          type: 'bar',
          data: {
            labels: ['Mesa', 'Recojo en tienda'],
            datasets: [{
              label: 'Total de pedidos',
              data: [1500, 2500],
              backgroundColor: ['rgba(255, 122, 0, 0.5)', 'rgba(255, 206, 86, 0.5)'],
              borderColor: ['rgba(255, 122, 0, 1)', 'rgba(255, 206, 86, 1)'],
              borderWidth: 1
            }]
          },
          options: {
            responsive: true,
            scales: {
              x: {
                beginAtZero: true
              },
              y: {
                beginAtZero: true
              }
            }
          }
        });
      }

      // Gráfico de propinas totales
      const ctxPropinas = document.getElementById('chartPropinas') as HTMLCanvasElement;
      if (ctxPropinas) {
        new Chart(ctxPropinas, {
          type: 'pie',
          data: {
            labels: ['Propinas Totales'],
            datasets: [{
              label: 'Propinas Totales',
              data: [500],
              backgroundColor: ['rgba(255, 122, 0, 0.5)'],
              borderColor: ['rgba(255, 122, 0, 1)'],
              borderWidth: 1
            }]
          },
          options: {
            responsive: true
          }
        });
      }
      
      // Gráfico de ventas mensuales
      const ctxVentas = document.getElementById('chartVentas') as HTMLCanvasElement;
      if (ctxVentas) {
        new Chart(ctxVentas, {
          type: 'line',
          data: {
            labels: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
            datasets: [{
              label: 'Ventas 2024',
              data: [30500, 28900, 32400, 35200, 38100, 40300, 42500, 45600, 43200, 41800, 0, 0],
              backgroundColor: 'rgba(255, 122, 0, 0.2)',
              borderColor: 'rgba(255, 122, 0, 1)',
              borderWidth: 2,
              tension: 0.3,
              fill: true
            }]
          },
          options: {
            responsive: true,
            scales: {
              y: {
                beginAtZero: true
              }
            }
          }
        });
      }
    };

    // Pequeño timeout para asegurar que los elementos del DOM estén disponibles
    const timer = setTimeout(() => {
      initCharts();
    }, 100);

    // Cleanup al desmontar el componente
    return () => {
      clearTimeout(timer);
      // Destruir las instancias de Chart si existen para evitar memory leaks
      Chart.getChart('chartPedidos')?.destroy();
      Chart.getChart('chartPropinas')?.destroy();
      Chart.getChart('chartVentas')?.destroy();
    };
  }, []);

  return (
    <MainLayout>
      {/* Sección de bienvenida */}
      <div className="logros-header">
        <h2>Mis Logros en FoodLy</h2>
        <p>Bienvenido, <strong>ADMIN</strong> - Panel de rendimiento y logros del restaurante</p>
      </div>
    
      {/* Sección de tarjetas de logros */}
      <div className="row mb-4">
        <div className="col-md-4">
          <div className="achievement-card">
            <div className="achievement-icon">
              <i className="fas fa-utensils"></i>
            </div>
            <h5>Total de Pedidos</h5>
            <p>Total de pedidos completados en el último mes</p>
            <div className="achievement-value">4,000</div>
            <div className="achievement-progress">
              <div className="achievement-progress-bar" style={{ width: '80%' }}></div>
            </div>
            <p>+15.2% desde el mes anterior</p>
          </div>
        </div>
        
        <div className="col-md-4">
          <div className="achievement-card">
            <div className="achievement-icon">
              <i className="fas fa-dollar-sign"></i>
            </div>
            <h5>Ingresos Totales</h5>
            <p>Ingresos generados en el último mes</p>
            <div className="achievement-value">S/ 42,500</div>
            <div className="achievement-progress">
              <div className="achievement-progress-bar" style={{ width: '75%' }}></div>
            </div>
            <p>+8.5% desde el mes anterior</p>
          </div>
        </div>
        
        <div className="col-md-4">
          <div className="achievement-card">
            <div className="achievement-icon">
              <i className="fas fa-users"></i>
            </div>
            <h5>Clientes Nuevos</h5>
            <p>Nuevos clientes registrados en el último mes</p>
            <div className="achievement-value">125</div>
            <div className="achievement-progress">
              <div className="achievement-progress-bar" style={{ width: '65%' }}></div>
            </div>
            <p>+12.3% desde el mes anterior</p>
          </div>
        </div>
      </div>
    
      {/* Sección de filtros */}
      <div className="filter-section mb-4">
        <h5><i className="fas fa-filter"></i> Filtrado</h5>
        <form>
          <div className="row mb-3">
            <div className="col-md-4">
              <label htmlFor="rango-fecha" className="form-label">Rango de Fecha:</label>
              <input type="text" id="rango-fecha" className="form-control" defaultValue="17/abr/2025 - 17/may/2025" readOnly />
            </div>
            <div className="col-md-8">
              <label htmlFor="filtrado-variable" className="form-label">Seleccionar variable de filtrado:</label>
              <div className="d-flex align-items-center">
                <div className="form-check me-3">
                  <input className="form-check-input" type="radio" name="filtrado" id="filtrado-ano" />
                  <label className="form-check-label" htmlFor="filtrado-ano">
                    Año
                  </label>
                </div>
                <div className="form-check me-3">
                  <input className="form-check-input" type="radio" name="filtrado" id="filtrado-mes" defaultChecked />
                  <label className="form-check-label" htmlFor="filtrado-mes">
                    Mes
                  </label>
                </div>
                <div className="form-check me-3">
                  <input className="form-check-input" type="radio" name="filtrado" id="filtrado-dia" />
                  <label className="form-check-label" htmlFor="filtrado-dia">
                    Día
                  </label>
                </div>
                <div className="form-check me-3">
                  <input className="form-check-input" type="radio" name="filtrado" id="filtrado-hora" />
                  <label className="form-check-label" htmlFor="filtrado-hora">
                    Hora
                  </label>
                </div>
              </div>
            </div>
          </div>
        </form>
      </div>
    
      {/* Sección de gráficos */}
      <div className="row mb-4">
        {/* Reporte de total de pedidos */}
        <div className="col-md-6 mb-4">
          <div className="chart-container">
            <h5 className="card-title"><i className="fas fa-chart-bar"></i> Reporte de Total de Pedidos</h5>
            <canvas id="chartPedidos"></canvas>
          </div>
        </div>
    
        {/* Reporte de propinas totales */}
        <div className="col-md-6 mb-4">
          <div className="chart-container">
            <h5 className="card-title"><i className="fas fa-chart-pie"></i> Reporte de Propinas Totales</h5>
            <canvas id="chartPropinas"></canvas>
          </div>
        </div>
      </div>
      
      {/* Gráfico de tendencia de ventas */}
      <div className="row">
        <div className="col-12">
          <div className="chart-container">
            <h5 className="card-title"><i className="fas fa-chart-line"></i> Tendencia de Ventas Mensuales</h5>
            <canvas id="chartVentas"></canvas>
          </div>
        </div>
      </div>
      
      {/* Sección de metas y logros */}
      <div className="row mt-4">
        <div className="col-12">
          <div className="filter-section">
            <h5><i className="fas fa-trophy"></i> Metas del Mes</h5>
            <div className="row">
              <div className="col-md-4 mb-3">
                <div className="d-flex justify-content-between mb-1">
                  <span>Meta de ventas</span>
                  <span>85%</span>
                </div>
                <div className="progress" style={{ height: '8px' }}>
                  <div className="progress-bar" role="progressbar" style={{ width: '85%', backgroundColor: 'var(--primary)' }} aria-valuenow={85} aria-valuemin={0} aria-valuemax={100}></div>
                </div>
              </div>
              <div className="col-md-4 mb-3">
                <div className="d-flex justify-content-between mb-1">
                  <span>Meta de clientes nuevos</span>
                  <span>65%</span>
                </div>
                <div className="progress" style={{ height: '8px' }}>
                  <div className="progress-bar" role="progressbar" style={{ width: '65%', backgroundColor: 'var(--primary)' }} aria-valuenow={65} aria-valuemin={0} aria-valuemax={100}></div>
                </div>
              </div>
              <div className="col-md-4 mb-3">
                <div className="d-flex justify-content-between mb-1">
                  <span>Meta de satisfacción</span>
                  <span>92%</span>
                </div>
                <div className="progress" style={{ height: '8px' }}>
                  <div className="progress-bar" role="progressbar" style={{ width: '92%', backgroundColor: 'var(--primary)' }} aria-valuenow={92} aria-valuemin={0} aria-valuemax={100}></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </MainLayout>
  )
}