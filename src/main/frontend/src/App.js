import './App.css';
import React, {useState, useEffect} from 'react';
import axios from 'axios';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import PlaylistsPage from "./Pages/PlaylistsPage";
import UserPage from "./Pages/UserPage";
import HomePage from "./Pages/HomePage";
import PlaylistPage from "./Pages/PlaylistPage";
import MyMenu from "./Elements/Menu";
import {Layout} from "antd";
import ArtistPage from "./Pages/ArtistPage";
import AlbumPage from "./Pages/AlbumPage";
import ContentPage from "./Pages/ContentPage";


const WhoAmI = () => {

  const [result, setResult] = useState({});

  const getStatus = () => {
    axios.get("http://localhost:8080/api/v1/whoami").then(res => {
      console.log(res.data);
      setResult(res.data);
    });
  }

  useEffect(() => {
    getStatus();
  }, []);
  return result;
}

function App() {
  let user = WhoAmI();
  if (user.role === "ANON")
    return <div><h1>Anonym</h1></div>
  return <div>
    <Router>
      <Switch>
        <Layout>
          {MyMenu(user)}
          <Route exact path={"/"} component={HomePage}></Route>
          <Route exact path={"/user/"} component={UserPage}></Route>
          <Route exact path={"/playlist/"} component={PlaylistsPage}></Route>
          <Route exact path="/playlist/id/:id" component={PlaylistPage}></Route>
          <Route exact path="/artist/id/:id" component={ArtistPage}></Route>
          <Route exact path="/album/id/:id" component={AlbumPage}></Route>
          <Route exact path="/owner/" component={ContentPage}/>
        </Layout>
      </Switch>
    </Router>
  </div>
}

export default App;
