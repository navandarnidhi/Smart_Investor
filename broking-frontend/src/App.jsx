import './App.css'
import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import AuthPage from "./pages/AuthPage";
import Dashboard from "./pages/Dashboard.jsx";
import KycPage from "./pages/KycPage";
import MarketData from "./pages/MarketData";
import Portfolio from "./pages/Portfolio";
import Funds from "./pages/Funds";
import Trading from "./pages/Trading";
import Watchlist from "./pages/Watchlist";
import AdminPanel from "./pages/AdminPanel";
import Navbar from './components/Navbar.jsx';
import Reports from './pages/Reports'; 


function App() {
  const isAuthenticated = !!localStorage.getItem("token");

  return (
    <Router>
      <Routes>
        <Route path="/auth/*" element={<AuthPage />} />
        <Route path="/dashboard" element={isAuthenticated ? <Dashboard /> : <Navigate to="/auth/login" />} />
        <Route path="/kyc" element={isAuthenticated ? <KycPage /> : <Navigate to="/auth/login" />} />
        <Route path="/market" element={<MarketData />} />
        <Route path="/portfolio" element={isAuthenticated ? <Portfolio /> : <Navigate to="/auth/login" />} />
        <Route path="/funds" element={isAuthenticated ? <Funds /> : <Navigate to="/auth/login" />} />
        <Route path="/trading" element={isAuthenticated ? <Trading /> : <Navigate to="/auth/login" />} />
        <Route path="/watchlist" element={isAuthenticated ? <Watchlist /> : <Navigate to="/auth/login" />} />
        <Route path="/admin" element={isAuthenticated ? <AdminPanel /> : <Navigate to="/auth/login" />} />
        <Route path="/navbar" element={<Navbar />} />
        <Route path="/reports" element={<Reports />} /> 
        <Route path="*" element={<Navigate to="/auth/login" />} />
      </Routes>
    </Router>
  );
}

export default App;
