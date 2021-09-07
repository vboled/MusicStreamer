import './App.css';
import React from 'react';
import axios from 'axios';
import {Button} from "antd";

function App() {

    const handleClick = () => {
        axios.get("http://localhost:8080/api/v1/whoami").then(res => {
            console.log(res.data)
        });
    }

    return (
      <div>
          <Button onClick={handleClick}>
              Request
          </Button>
      </div>
    );
}

export default App;
