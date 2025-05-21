"use client";

import Link from "next/link";
import React, { useState, useEffect, ReactNode } from "react";
import { useBreakpoints } from "@/hooks/useBreakpoints";
import { motion, AnimatePresence } from "framer-motion";
import { cn } from "@/utils/cn";
import "@/styles/sidebar-fix.css"; // Importar estilos adicionales para solucionar el problema
// Importación de iconos de React Icons
import { 
  FaBars, 
  FaTimes, 
  FaUtensils, 
  FaShoppingCart,
  FaBox, 
  FaUsers, 
  FaHome, 
  FaSignOutAlt,
  FaChevronDown,
  FaThLarge,
  FaChartBar,
  FaChartPie,
  FaClipboard
} from "react-icons/fa";
import { 
  FiFileText, 
  FiPackage
} from "react-icons/fi";

// Definición de los tipos para los elementos del menú
type MenuItem = {
  name: string;
  icon: ReactNode;
  path?: string;
  submenu?: SubMenuItem[];
};

type SubMenuItem = {
  name: string;
  icon: ReactNode;
  path: string;
};

export default function Sidebar() {  // Inicializamos el estado con los menús cerrados
  const [openSubmenus, setOpenSubmenus] = useState<{ [key: string]: boolean }>({});
  const [sidebarOpen, setSidebarOpen] = useState(true);
  const { isMobile, isTablet } = useBreakpoints();
  // Estado para detectar si estamos en el cliente
  const [isClient, setIsClient] = useState(false);
  
  // Detectar renderizado del lado del cliente
  useEffect(() => {
    setIsClient(true);
  }, []);

  // Menú de navegación
  const menuItems: MenuItem[] = [
    {
      name: "Caja",
      icon: <FaThLarge className="me-2" />,
      submenu: [
        { name: "Arqueo", icon: <FaClipboard className="me-2" />, path: "/arqueo" },
        { name: "Apertura y cierre", icon: <FaBox className="me-2" />, path: "/apertura-cierre" },
      ]
    },
    {
      name: "Pedido",
      icon: <FaShoppingCart className="me-2" />,
      path: "/pedido"
    },
    {
      name: "Reportes",
      icon: <FiFileText className="me-2" />,
      submenu: [
        { name: "Consolidados", icon: <FaChartBar className="me-2" />, path: "/consolidados" },
        { name: "Gráficos", icon: <FaChartPie className="me-2" />, path: "/graficos" },
      ]
    },
    {
      name: "Productos",
      icon: <FiPackage className="me-2" />,
      submenu: [
        { name: "Productos", icon: <FaChartBar className="me-2" />, path: "/productos" },
        { name: "Inventario", icon: <FaChartPie className="me-2" />, path: "/inventario" },
      ]
    },
    {
      name: "Empleados",
      icon: <FaUsers className="me-2" />,
      path: "/empleados"
    },
    {
      name: "Cocina",
      icon: <FaUtensils className="me-2" />,
      path: "/cocina"
    },
    {
      name: "Logros",
      icon: <FaHome className="me-2" />,
      path: "/logros"
    },
    {
      name: "Salir",
      icon: <FaSignOutAlt className="me-2" />,
      path: "/"
    },
  ];
  // Inicializar los estados de los submenús
  useEffect(() => {
    // Crear un objeto con todos los submenús establecidos en false inicialmente
    const initialSubmenuState: { [key: string]: boolean } = {};
    menuItems.forEach(item => {
      if (item.submenu) {
        initialSubmenuState[item.name] = false;
      }
    });
    console.log("Inicialización de submenús:", initialSubmenuState);
    setOpenSubmenus(initialSubmenuState);
  }, []); // Este efecto se ejecuta solo al montar el componente

  // Actualizar estado de sidebar basado en el tamaño de la pantalla
  useEffect(() => {
    if (isMobile || isTablet) {
      setSidebarOpen(false);
    } else {
      setSidebarOpen(true);
    }
  }, [isMobile, isTablet]);

  // Función mejorada para toggle de submenús
  const toggleSubmenu = (name: string, e: React.MouseEvent) => {
    // Detener la propagación del evento
    e.preventDefault();
    e.stopPropagation();
    
    // Log para depuración
    console.log(`Toggling submenu: ${name} - Current state:`, openSubmenus[name]);
    
    // Actualizar el estado para abrir o cerrar el submenú
    setOpenSubmenus((prev) => {
      const newState = { ...prev, [name]: !prev[name] };
      console.log("New submenu state:", newState);
      return newState;
    });
  };

  const toggleSidebar = () => {
    setSidebarOpen(!sidebarOpen);
  };

  // Variantes para animaciones
  const sidebarVariants = {
    open: {
      x: 0,
      transition: {
        type: "spring",
        stiffness: 300,
        damping: 30
      }
    },
    closed: {
      x: "-100%",
      transition: {
        type: "spring",
        stiffness: 300,
        damping: 30
      }
    }
  };

  return (
    <>
      {/* Botón para toggle de sidebar en dispositivos móviles */}      <motion.button 
        className="position-fixed d-lg-none btn rounded-circle shadow-sm"
        animate={isClient ? {
          left: sidebarOpen ? "260px" : "20px",
          top: "20px"
        } : {}}
        initial={{
          left: "20px",
          top: "20px"
        }}
        transition={{
          type: "spring",
          stiffness: 500,
          damping: 30
        }}
        onClick={toggleSidebar}
        style={{
          zIndex: 1060,
          width: "45px",
          height: "45px",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          backgroundColor: "var(--primary)",
          border: "none"
        }}
      >
        {sidebarOpen ? 
          <FaTimes className="text-white" size={20} /> : 
          <FaBars className="text-white" size={20} />
        }
      </motion.button>

      {/* Sidebar */}      <motion.div 
        id="sidebar"
        className={cn("sidebar", { "sidebar-open": sidebarOpen, "sidebar-closed": !sidebarOpen })}
        initial={isClient ? "closed" : false}
        animate={isClient ? (sidebarOpen || (!isMobile && !isTablet) ? "open" : "closed") : false}
        variants={isClient && (isMobile || isTablet) ? sidebarVariants : {}}
      >
        <Link href="/" className="brand-link">
          <FaUtensils className="me-2" size={24} />
          <span>FoodLy</span>
        </Link>

        <ul className="nav-list">
          {menuItems.map((item, index) => (
            <li key={`menu-item-${index}`} className="nav-item">
              {item.submenu ? (
                <>
                  <button
                    className={cn("nav-link", { active: openSubmenus[item.name] })}
                    onClick={(e) => toggleSubmenu(item.name, e)}
                    aria-expanded={openSubmenus[item.name] ? "true" : "false"}
                  >
                    {item.icon}
                    <span className="nav-text">{item.name}</span>
                    <FaChevronDown 
                      className={cn("ms-auto", { "rotate-180": openSubmenus[item.name] })} 
                      size={12} 
                    />
                  </button>                  <AnimatePresence>
                    {openSubmenus[item.name] && (
                      <motion.ul 
                        className="submenu"
                        initial={{ height: 0, opacity: 0 }}
                        animate={{ height: "auto", opacity: 1 }}
                        exit={{ height: 0, opacity: 0 }}
                        transition={{ duration: 0.3 }}
                        style={{ display: "block", overflow: "visible" }}
                      >
                        {item.submenu.map((subItem, subIndex) => (
                          <li key={`submenu-item-${subIndex}`}>
                            <Link href={subItem.path} className="submenu-link">
                              {subItem.icon}
                              <span>{subItem.name}</span>
                            </Link>
                          </li>
                        ))}
                      </motion.ul>
                    )}
                  </AnimatePresence>
                </>
              ) : (
                <Link href={item.path || "#"} className="nav-link">
                  {item.icon}
                  <span className="nav-text">{item.name}</span>
                </Link>
              )}
            </li>
          ))}
        </ul>
      </motion.div>
    </>
  );
}
