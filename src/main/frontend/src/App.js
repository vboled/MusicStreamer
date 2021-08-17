import './App.css';
import React, {useState, useEffect} from 'react';
import axios from 'axios';
import Cookie from 'js-cookie'
import SignedPage from "./Pages/SignedPage";
import {BrowserRouter as Router, Link, Route, Switch, useHistory} from "react-router-dom";
import PlaylistPage from "./Pages/PlaylistPage";
import UserPage from "./Pages/UserPage";

const WhoAmI = () => {

  const [result, setResult] = useState({user:{}, playlistLists: []});

  const getStatus = () => {
    axios.get("http://localhost:8080/api/v1/whoami").then(res => {
      console.log(res.data);
      console.log(res.data);
      setResult(res.data);
    });
  }

  useEffect(() => {
    getStatus();
  }, []);

  return SignedPage(result)
}

function App() {
  return (
    <Router>
      <div className="App">
      <Switch>
        <Route exact path="/" component={WhoAmI}></Route>
        <Route exact path="/playlist/:id" component={PlaylistPage}></Route>
        <Route exact path="/user/:username" component={UserPage}></Route>
      </Switch>
      </div>
    </Router>
  );
}

const Header = () => {
  return (<div><h1>Test</h1></div>);
}

export default App;
