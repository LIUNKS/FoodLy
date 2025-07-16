"use client";

import React, { useEffect, useState } from "react";
import "@/styles/common.css";
import Cookies from "js-cookie";
import axios from "axios";
import { API_BASE_URL } from "@/services/api";

interface Product {
  id: number;
  name: string;
  category: string;
  price: number;
}

interface CartItem {
  product: Product;
  quantity: number;
}

export default function PedidoForm() {
  const [categories, setCategories] = useState<string[]>([]);
  const [products, setProducts] = useState<Product[]>([]);
  const [filteredProducts, setFilteredProducts] = useState<Product[]>([]);
  const [selectedCategory, setSelectedCategory] = useState<string>("");
  const [selectedProduct, setSelectedProduct] = useState<string>("");
  const [cart, setCart] = useState<CartItem[]>([]);
  const [cliente, setCliente] = useState<string>("Jorge");

  const [categoryError, setCategoryError] = useState<string>("");
  useEffect(() => {
    async function fetchCategories() {
      try {
        setCategoryError("");
        const localToken = typeof window !== "undefined" ? localStorage.getItem("authToken") : null;
        const cookieToken = Cookies.get("token");
        const userToken = localToken || cookieToken || null;
        if (!userToken) {
          setCategories([]);
          setProducts([]);
          setCategoryError("No tienes permisos para ver las categorías de productos.");
          return;
        }
        const PRODUCT_API_URL = `${API_BASE_URL}product`;
        const res = await axios.get(`${PRODUCT_API_URL}/list?page=0`, {
          headers: { Authorization: `Bearer ${userToken}` },
        });
        if (res.data && res.data.status === 200) {
          const productsData = res.data.data || [];
          if (productsData.length === 0) {
            setCategoryError("No tienes permisos para ver las categorías de productos o no hay productos disponibles.");
          }
          const mappedProducts: Product[] = productsData.map((p: any) => ({
            id: p.id_product,
            name: p.name_product,
            category: p.category,
            price: p.price,
          }));
          setProducts(mappedProducts);
          const uniqueCategories = Array.from(new Set(mappedProducts.map((p) => p.category)));
          setCategories(uniqueCategories);
        } else {
          setCategories([]);
          setProducts([]);
          setCategoryError("No tienes permisos para ver las categorías de productos.");
        }
      } catch (error: any) {
        setCategories([]);
        setProducts([]);
        if (error?.response?.status === 403 || error?.response?.status === 401) {
          setCategoryError("No tienes permisos para ver las categorías de productos.");
        } else {
          setCategoryError("Error al cargar las categorías. Intenta nuevamente.");
        }
      }
    }
    fetchCategories();
  }, []);

  // Filtrar productos por categoría seleccionada
  useEffect(() => {
    async function fetchProductsByCategory() {
      if (!selectedCategory) {
        setFilteredProducts([]);
        return;
      }
      try {
        const localToken = typeof window !== "undefined" ? localStorage.getItem("authToken") : null;
        const cookieToken = Cookies.get("token");
        const userToken = localToken || cookieToken || null;
        if (!userToken) {
          setFilteredProducts([]);
          return;
        }
        const PRODUCT_API_URL = `${API_BASE_URL}product`;
        const res = await axios.get(`${PRODUCT_API_URL}/list/category`, {
          params: { category: selectedCategory, page: 0 },
          headers: { Authorization: `Bearer ${userToken}` },
        });
        if (res.data && res.data.status === 200) {
          const productsData = res.data.data || [];
          const mappedProducts: Product[] = productsData.map((p: any) => ({
            id: p.id_product,
            name: p.name_product,
            category: p.category,
            price: p.price,
          }));
          setFilteredProducts(mappedProducts);
        } else {
          setFilteredProducts([]);
        }
      } catch (error) {
        setFilteredProducts([]);
      }
    }
    fetchProductsByCategory();
  }, [selectedCategory]);

  const handleCategoryChange = (category: string) => {
    setSelectedCategory(category);
    setSelectedProduct("");
  };

  const addToCart = () => {
    if (selectedProduct) {
      const product = products.find(p => p.id.toString() === selectedProduct);
      if (product) {
        const existingItem = cart.find(item => item.product.id === product.id);
        if (existingItem) {
          setCart(cart.map(item => 
            item.product.id === product.id 
              ? { ...item, quantity: item.quantity + 1 }
              : item
          ));
        } else {
          setCart([...cart, { product, quantity: 1 }]);
        }
      }
    }
  };

  const removeFromCart = (productId: number) => {
    setCart(cart.filter(item => item.product.id !== productId));
  };

  const updateQuantity = (productId: number, quantity: number) => {
    if (quantity <= 0) {
      removeFromCart(productId);
    } else {
      setCart(cart.map(item => 
        item.product.id === productId 
          ? { ...item, quantity }
          : item
      ));
    }
  };

  const clearCart = () => {
    setCart([]);
  };

  const calculateSubtotal = () => {
    return cart.reduce((total, item) => total + (item.product.price * item.quantity), 0);
  };

  const calculateTax = () => {
    return calculateSubtotal() * 0.1;
  };

  const calculateTotal = () => {
    return calculateSubtotal() + calculateTax();
  };

  return (
    <div className="foodly-card fade-in-up">
      <div className="foodly-card-header">
        <button className="foodly-btn w-100">
          <i className="fas fa-check-circle me-2"></i>Finalizar Pedido
        </button>
      </div>
      <div className="foodly-card-body">
        <div className="mb-4" style={{ position: 'relative' }}>
          <label htmlFor="cliente" className="foodly-form-label" style={{ fontWeight: 500, color: 'var(--primary)' }}>
            <i className="fas fa-user me-2" style={{ fontSize: '1.1em', verticalAlign: 'middle' }}></i>
            Cliente
          </label>
          <div style={{ position: 'relative' }}>
            <input
              type="text"
              id="cliente"
              className="foodly-form-control"
              style={{ borderRadius: 8, border: '1.5px solid var(--primary-light)', paddingLeft: 40, fontSize: '1.05em', boxShadow: '0 1px 4px rgba(0,0,0,0.04)' }}
              value={cliente}
              onChange={(e) => setCliente(e.target.value)}
              placeholder="Nombre del cliente..."
            />
            <span style={{ position: 'absolute', left: 12, top: '50%', transform: 'translateY(-50%)', color: 'var(--primary)', pointerEvents: 'none' }}>
              <i className="fas fa-user"></i>
            </span>
          </div>
        </div>
        
        <ul className="nav nav-tabs" style={{ borderBottom: '2px solid var(--primary-light)' }}>
          <li className="nav-item">
            <a 
              className="nav-link active" 
              data-bs-toggle="tab" 
              href="#categorias"
              style={{ color: 'var(--primary)', fontWeight: 500 }}>
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
            <label htmlFor="categorias-select" className="foodly-form-label" style={{ fontWeight: 500, color: 'var(--primary)' }}>
              <i className="fas fa-folder me-2" style={{ fontSize: '1.1em', verticalAlign: 'middle' }}></i>
              Categoría(s)
            </label>
            {categoryError ? (
              <div className="alert alert-warning text-center" role="alert" style={{ marginTop: 10 }}>
                <i className="fas fa-exclamation-triangle me-2"></i>
                {categoryError}
              </div>
            ) : (
              <select
                className="foodly-form-control"
                id="categorias-select"
                style={{ borderRadius: 8, border: '1.5px solid var(--primary-light)', fontSize: '1.05em', boxShadow: '0 1px 4px rgba(0,0,0,0.04)' }}
                value={selectedCategory}
                onChange={(e) => handleCategoryChange(e.target.value)}
              >
                <option value="">Seleccione una categoría...</option>
                {categories.map((cat) => (
                  <option key={cat} value={cat}>{cat}</option>
                ))}
              </select>
            )}
          </div>
          
          <div id="productos" className="tab-pane fade">
            {!selectedCategory ? (
              <div className="alert alert-warning text-center" role="alert" style={{ marginTop: 20 }}>
                <i className="fas fa-exclamation-triangle me-2"></i>
                Primero debes seleccionar una categoría para poder ver los productos disponibles.
              </div>
            ) : (
              <>
                <label htmlFor="productos-select" className="foodly-form-label" style={{ fontWeight: 500, color: 'var(--primary)' }}>
                  <i className="fas fa-utensils me-2" style={{ fontSize: '1.1em', verticalAlign: 'middle' }}></i>
                  Producto(s)
                </label>
                <select
                  className="foodly-form-control"
                  id="productos-select"
                  style={{ borderRadius: 8, border: '1.5px solid var(--primary-light)', fontSize: '1.05em', boxShadow: '0 1px 4px rgba(0,0,0,0.04)' }}
                  value={selectedProduct}
                  onChange={(e) => setSelectedProduct(e.target.value)}
                >
                  <option value="">Seleccione un producto...</option>
                  {filteredProducts.map((product) => (
                    <option key={product.id} value={product.id}>
                      {product.name} - S/.{product.price.toFixed(2)}
                    </option>
                  ))}
                </select>
                <div className="mt-3">
                  <button 
                    className="foodly-btn" 
                    onClick={addToCart}
                    disabled={!selectedProduct}
                  >
                    <i className="fas fa-plus me-2"></i>Agregar al Carrito
                  </button>
                </div>
              </>
            )}
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
              <tbody>
                {cart.map((item) => (
                  <tr key={item.product.id}>
                    <td>{item.product.name}</td>
                    <td>
                      <div className="input-group" style={{ width: '120px' }}>
                        <button 
                          className="btn btn-outline-secondary btn-sm"
                          onClick={() => updateQuantity(item.product.id, item.quantity - 1)}
                        >
                          -
                        </button>
                        <input 
                          type="number" 
                          className="form-control form-control-sm text-center"
                          value={item.quantity}
                          min="1"
                          onChange={(e) => updateQuantity(item.product.id, parseInt(e.target.value) || 0)}
                        />
                        <button 
                          className="btn btn-outline-secondary btn-sm"
                          onClick={() => updateQuantity(item.product.id, item.quantity + 1)}
                        >
                          +
                        </button>
                      </div>
                    </td>
                    <td>S/.{item.product.price.toFixed(2)}</td>
                    <td>S/.{(item.product.price * item.quantity).toFixed(2)}</td>
                    <td>
                      <button 
                        className="btn btn-outline-danger btn-sm"
                        onClick={() => removeFromCart(item.product.id)}
                      >
                        <i className="fas fa-trash"></i>
                      </button>
                    </td>
                  </tr>
                ))}
                {cart.length === 0 && (
                  <tr>
                    <td colSpan={5} className="text-center text-muted">
                      No hay productos en el carrito
                    </td>
                  </tr>
                )}
              </tbody>
              <tfoot style={{ backgroundColor: 'var(--background)', fontWeight: 500 }}>
                <tr>
                  <td colSpan={3} className="text-end">
                    Subtotal
                  </td>
                  <td colSpan={2} className="text-start">
                    S/.{calculateSubtotal().toFixed(2)}
                  </td>
                </tr>
                <tr>
                  <td colSpan={3} className="text-end">
                    Impuesto (10%)
                  </td>
                  <td colSpan={2} className="text-start">
                    S/.{calculateTax().toFixed(2)}
                  </td>
                </tr>
                <tr style={{ color: 'var(--primary)', fontWeight: 600 }}>
                  <td colSpan={3} className="text-end">
                    Total
                  </td>
                  <td colSpan={2} className="text-start">
                    S/.{calculateTotal().toFixed(2)}
                  </td>
                </tr>
              </tfoot>
            </table>
          </div>
          <div className="d-flex justify-content-end mt-3">
            <button 
              className="foodly-btn me-2" 
              style={{ backgroundColor: '#6c757d' }}
              onClick={clearCart}
            >
              <i className="fas fa-trash-alt me-2"></i>Limpiar Carrito
            </button>
            <button 
              className="foodly-btn"
              disabled={cart.length === 0}
            >
              <i className="fas fa-check-circle me-2"></i>Finalizar Pedido
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}