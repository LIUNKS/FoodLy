"use client"
import { useEffect, useState } from 'react';
import { Empleado } from '../types/empleado';
import { getAllEmpleados, createEmpleado, deleteEmpleado, updateEmpleado } from '../services/empleadoService';
import DataTable from "@/components/common/DataTable"
import ModalPortal from "@/components/common/ModalPortal"
import { toast, ToastContainer } from "react-toastify"
import Swal from "sweetalert2"
import withReactContent from "sweetalert2-react-content"
import "react-toastify/dist/ReactToastify.css"
import Cookies from "js-cookie"
const MySwal = withReactContent(Swal);

export default function EmpleadosTable() {
    const [empleados, setEmpleados] = useState<Empleado[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [token, setToken] = useState<string | null>(null); // Simulación de token, debería obtenerse de un contexto o estado global
    // Estado del formulario de producto (crear o editar)
    const [formData, setFormData] = useState<Partial<Empleado>>({})
    const [paginaActual, setPaginaActual] = useState<number>(0);
    useEffect(() => {
        const localToken = typeof window !== "undefined" ? localStorage.getItem("authToken") : null
        const cookieToken = Cookies.get("token")
        const userCookie = localToken || cookieToken || null
        setToken(userCookie)
        if (userCookie) {
            loadEmpleados(userCookie, paginaActual)
        }
        // Cargar empleados al montar el componente
        // loadEmpleados();
    }, [paginaActual]);
    const loadEmpleados = async (token: string, page: number) => {
        setLoading(true);
        try {
            const res = await getAllEmpleados(token, page);
            if (res.data.status === 200) {
                setEmpleados(res.data.data);
            } else {
                setEmpleados([]);
            }
        } catch (error) {
            console.error("Error cargando empleados", error);
            setEmpleados([]);
        } finally {
            setLoading(false);
        }
    };
    // Abrir modal y rellenar datos si es edición
    const openModal = (empleado: Empleado | null) => {
        setFormData(empleado || {})
        const modal = document.getElementById("empleadoModal");
        modal && new (window as any).bootstrap.Modal(modal).show()
    }

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
        setFormData({ ...formData, [e.target.name]: e.target.value })
    }
    // Guardar o actualizar producto
    const handleSubmit = async () => {
        if (!token) return
        const userCookie = Cookies.get("user")
        const idAdmin = userCookie ? JSON.parse(userCookie).id : null

        try {
            if (formData.id_atm) {
                await updateEmpleado(formData.id_atm, formData, token)
                toast.success("Producto actualizado")
            } else {
                await createEmpleado(formData as Empleado, idAdmin, token)
                toast.success("Producto creado")
            }

            // Cerrar modal después de guardar
            const modalEl = document.getElementById("empleadoModal")
            modalEl && (window as any).bootstrap.Modal.getInstance(modalEl).hide()
            const response = await getAllEmpleados(token, 0)
            setEmpleados(response.data.data)
        } catch {
            toast.error("Error al guardar producto")
        }
    }

    // Confirmar y eliminar producto
    const handleDelete = async (id: string) => {
        if (!token) return
        const confirm = await MySwal.fire({
            title: "¿Eliminar empleado?",
            text: "Esta acción no se puede deshacer.",
            icon: "warning",
            showCancelButton: true,
            confirmButtonText: "Sí, eliminar",
            cancelButtonText: "Cancelar",
        })

        if (confirm.isConfirmed) {
            try {
                await deleteEmpleado(id, token)
                toast.success("Producto eliminado")
                const response = await getAllEmpleados(token, 0)
                setEmpleados(response.data.data)
            } catch {
                toast.error("Error al eliminar")
            }
        }
    }
    return (
        <div className="fade-in-up container-fluid">
            {/* Título y acción principal */}
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2 className="fw-bold">Gestión de Empleados</h2>
                <button className="btn btn-primary" onClick={() => openModal(null)}>
                    <i className="bi bi-plus-circle me-1" /> Nuevo Empleado
                </button>
            </div>
            {/* Tabla de empleados */}
            <DataTable headers={["#", "dni", "Nombre", "Alias", "email","telefono", "Acciones"]} title="Listado de Empleados">
                {loading ? (
                    <tr><td colSpan={5} className="text-center">Cargando empleados...</td></tr>
                ) : empleados.length === 0 ? (
                    <tr><td colSpan={5} className="text-center">No hay empleados registrados.</td></tr>
                ) : (
                    empleados.map((empleado, i) => (
                        <tr key={empleado.id_atm}>
                            <td>{i + 1}</td>
                            <td>{empleado.dni}</td>
                            <td>{empleado.name_atm}</td>
                            <td>{empleado.alias}</td>
                            <td>{empleado.email}</td>
                            <td>{empleado.phone}</td>
                            <td>
                                <button className="btn btn-sm btn-outline-primary me-2" onClick={() => openModal(empleado)}>
                                    <i className="bi bi-pencil" /> Editar
                                </button>
                                <button className="btn btn-sm btn-outline-danger" onClick={() => handleDelete(empleado.id_atm)}>
                                    <i className="bi bi-trash" /> Eliminar
                                </button>
                            </td>
                        </tr>
                    ))
                )}
                {/* Paginación */}
                <tr>
                    <td colSpan={5}>
                        <div className="d-flex justify-content-between align-items-center mt-4 px-2">
                            <div className="text-muted">
                                Página <strong>{paginaActual + 1}</strong>
                            </div>
                            <nav>
                                <ul className="pagination pagination-sm mb-0">
                                    <li className={`page-item ${paginaActual === 0 ? "disabled" : ""}`}>
                                        <button
                                            className="page-link"
                                            onClick={() => setPaginaActual(prev => Math.max(prev - 1, 0))}
                                        >
                                            <i className="bi bi-chevron-left" /> Anterior
                                        </button>
                                    </li>
                                    <li className="page-item active">
                                        <span className="page-link">{paginaActual + 1}</span>
                                    </li>
                                    <li className={`page-item ${empleados.length < 10 ? "disabled" : ""}`}>
                                        <button
                                            className="page-link"
                                            onClick={() => setPaginaActual(prev => prev + 1)}
                                        >
                                            Siguiente <i className="bi bi-chevron-right" />
                                        </button>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </td>
                </tr>
            </DataTable>
            {/* Modal de empleado mejorado */}
            <ModalPortal>
            <div className="modal fade" id="atmModal" tabIndex={-1}>
                <div className="modal-dialog modal-lg modal-dialog-centered">
                <div className="modal-content shadow-lg border-0 rounded-4">
                    <div className="modal-header bg-primary text-white rounded-top-4">
                    <h5 className="modal-title d-flex align-items-center gap-2">
                        <i className="fa-solid fa-user-tie"></i>
                        {formData.id_atm ? "Editar Cajero" : "Nuevo Cajero"}
                    </h5>
                    <button
                        type="button"
                        className="btn-close btn-close-white"
                        data-bs-dismiss="modal"
                    />
                    </div>
                    <div className="modal-body p-4">
                    <div className="mb-3">
                        <label htmlFor="name_atm" className="form-label">Nombre completo</label>
                        <input
                        type="text"
                        name="name_atm"
                        value={formData.name_atm || ""}
                        onChange={handleChange}
                        className="form-control"
                        placeholder="Ej. Carlos Ramírez"
                        />
                    </div>

                    <div className="mb-3">
                        <label htmlFor="alias" className="form-label">Alias</label>
                        <input
                        type="text"
                        name="alias"
                        value={formData.alias || ""}
                        onChange={handleChange}
                        className="form-control"
                        placeholder="Ej. cramirez"
                        />
                    </div>

                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">Correo electrónico</label>
                        <input
                        type="email"
                        name="email"
                        value={formData.email || ""}
                        onChange={handleChange}
                        className="form-control"
                        placeholder="Ej. cramirez@atmcorp.com"
                        />
                    </div>

                    <div className="mb-3">
                        <label htmlFor="phone" className="form-label">Teléfono</label>
                        <input
                        type="text"
                        name="phone"
                        value={formData.phone || ""}
                        onChange={handleChange}
                        className="form-control"
                        placeholder="Ej. 987654321"
                        />
                    </div>

                    <div className="mb-3">
                        <label htmlFor="dni" className="form-label">DNI</label>
                        <input
                        type="text"
                        name="dni"
                        value={formData.dni || ""}
                        onChange={handleChange}
                        className="form-control"
                        placeholder="Ej. 72145678"
                        />
                    </div>
                    </div>

                    <div className="modal-footer p-3">
                    <button className="btn btn-secondary" data-bs-dismiss="modal">
                        <i className="fa-solid fa-xmark me-1"></i> Cancelar
                    </button>
                    <button className="btn btn-primary" onClick={handleSubmit}>
                        <i className="fa-solid fa-floppy-disk me-1"></i> Guardar
                    </button>
                    </div>
                </div>
                </div>
            </div>
            </ModalPortal>

            {/* Notificaciones tipo Toast */}
            <ToastContainer position="top-right" autoClose={3000} />
        </div>
    )
}
