import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { getToken, logout } from '../utils/auth';

function Navbar() {
  const isAuthenticated = !!getToken();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/auth/login');
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark px-3">
      <Link className="navbar-brand" to="/">
        BrokingApp
      </Link>
      <div className="collapse navbar-collapse">
        <ul className="navbar-nav me-auto">
          {isAuthenticated && (
            <>
              <li className="nav-item">
                <Link className="nav-link" to="/dashboard">Dashboard</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/portfolio">Portfolio</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/trading">Trading</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/funds">Funds</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/watchlist">Watchlist</Link>
              </li>
            </>
          )}
        </ul>
        <ul className="navbar-nav">
          {isAuthenticated ? (
            <li className="nav-item">
              <button className="btn btn-sm btn-outline-light" onClick={handleLogout}>
                Logout
              </button>
            </li>
          ) : (
            <li className="nav-item">
              <Link className="btn btn-sm btn-outline-light" to="/auth/login">
                Login
              </Link>
            </li>
          )}
        </ul>
      </div>
    </nav>
  );
}

export default Navbar;
