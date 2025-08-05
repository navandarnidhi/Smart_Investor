import React, { useEffect, useState } from 'react';
import axios from 'axios';

function MarketData() {
  const [indices, setIndices] = useState({});
  const [symbol, setSymbol] = useState('RELIANCE');
  const [stockData, setStockData] = useState(null);

  // Fetch index data (like NIFTY, SENSEX)
  useEffect(() => {
    axios.get('http://localhost:8080/api/marketdata/indices')
      .then((res) => setIndices(res.data))
      .catch((err) => {
        console.error('Failed to fetch indices:', err);
        alert('Error fetching index data');
      });
  }, []);

  // Fetch individual stock data
  const fetchStockData = async () => {
    try {
      const res = await axios.get(`http://localhost:8080/api/marketdata/stocks/${symbol}`);
      setStockData(res.data);
    } catch (err) {
      alert('Error fetching stock data');
      console.error(err);
    }
  };

  return (
    <div className="container mt-4">
      <h3>Market Overview</h3>

      <h5 className="mt-3">Indices</h5>
      <ul>
        {Object.entries(indices).map(([name, value], i) => (
          <li key={i}><strong>{name}</strong>: {value}</li>
        ))}
      </ul>

      <div className="mt-4">
        <h5>Stock Lookup</h5>
        <input
          className="form-control mb-2"
          placeholder="Enter stock symbol"
          value={symbol}
          onChange={(e) => setSymbol(e.target.value.toUpperCase())}
        />
        <button className="btn btn-primary mb-3" onClick={fetchStockData}>
          Fetch Stock
        </button>

        {stockData && (
          <pre style={{ background: '#f8f9fa', padding: '10px' }}>
            {JSON.stringify(stockData, null, 2)}
          </pre>
        )}
      </div>
    </div>
  );
}

export default MarketData;
