"use client";

import { ReactNode, useState } from "react";
import "@/styles/common.css";
import { motion } from "framer-motion";
import { cn } from "@/utils/cn";
import { FiSearch, FiDownload, FiFilter } from "react-icons/fi";

interface DataTableProps {
  headers: string[];
  children: ReactNode;
  className?: string;
  title?: string;
  searchable?: boolean;
  exportable?: boolean;
  filterable?: boolean;
  pagination?: boolean;
  emptyMessage?: string;
}

export default function DataTable({ 
  headers, 
  children, 
  className = "", 
  title,
  searchable = false,
  exportable = false,
  filterable = false,
  pagination = false,
  emptyMessage = "No hay datos disponibles"
}: DataTableProps) {
  const [searchTerm, setSearchTerm] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 10;

  const containerVariants = {
    hidden: { opacity: 0, y: 20 },
    visible: { 
      opacity: 1, 
      y: 0,
      transition: {
        duration: 0.4,
        when: "beforeChildren",
        staggerChildren: 0.1
      }
    }
  };

  const itemVariants = {
    hidden: { opacity: 0, y: 10 },
    visible: { opacity: 1, y: 0 }
  };

  return (
    <motion.div 
      className="foodly-card shadow-sm"
      variants={containerVariants}
      initial="hidden"
      animate="visible"
    >
      {title && (
        <div className="foodly-card-header d-flex justify-content-between align-items-center">
          <motion.h3 className="mb-0 fs-5" variants={itemVariants}>{title}</motion.h3>
          
          {(searchable || exportable || filterable) && (
            <motion.div className="table-actions d-flex gap-2" variants={itemVariants}>
              {searchable && (
                <div className="position-relative">
                  <FiSearch className="position-absolute text-muted" style={{ left: "10px", top: "50%", transform: "translateY(-50%)" }} />
                  <input
                    type="text"
                    className="form-control form-control-sm ps-4"
                    placeholder="Buscar..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    style={{ width: "180px" }}
                  />
                </div>
              )}
              
              {filterable && (
                <button className="btn btn-sm btn-outline-secondary">
                  <FiFilter size={14} className="me-1" /> Filtros
                </button>
              )}
              
              {exportable && (
                <button className="btn btn-sm btn-outline-primary">
                  <FiDownload size={14} className="me-1" /> Exportar
                </button>
              )}
            </motion.div>
          )}
        </div>
      )}
      <motion.div className="foodly-card-body px-0 pt-0" variants={itemVariants}>
        <div className="table-responsive">
          <table className={cn("table table-hover table-sm", className)}>
            <thead style={{ backgroundColor: 'var(--primary-light)' }}>
              <tr>
                {headers.map((header, index) => (
                  <th 
                    key={index} 
                    scope="col" 
                    className="px-3 py-3"
                    style={{ color: 'var(--text-dark)', fontWeight: '600' }}
                  >
                    {header}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody className="align-middle">
              {children || (
                <tr>
                  <td colSpan={headers.length} className="text-center py-4 text-muted">
                    {emptyMessage}
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
        
        {pagination && (
          <motion.div 
            className="d-flex justify-content-between align-items-center px-3 py-2 border-top"
            variants={itemVariants}
          >
            <div className="text-muted small">
              Mostrando página {currentPage}
            </div>
            <nav aria-label="Table navigation">
              <ul className="pagination pagination-sm mb-0">
                <li className={cn("page-item", { "disabled": currentPage === 1 })}>
                  <button 
                    className="page-link" 
                    onClick={() => setCurrentPage(p => Math.max(1, p - 1))}
                    disabled={currentPage === 1}
                  >
                    Anterior
                  </button>
                </li>
                <li className="page-item active">
                  <span className="page-link">{currentPage}</span>
                </li>
                <li className="page-item">
                  <button 
                    className="page-link" 
                    onClick={() => setCurrentPage(p => p + 1)}
                  >
                    Siguiente
                  </button>
                </li>
              </ul>
            </nav>
          </motion.div>        )}
      </motion.div>
    </motion.div>
  );
}
