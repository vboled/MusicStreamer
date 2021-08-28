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
import SearchPage from "./Pages/SearchPage";
import {Header} from "antd/es/layout/layout";


function App() {

  const [userView, setUserView] = useState({user:{}, playlistLists:[]})

  const whoAmI = () => {
    axios.get("http://localhost:8080/api/v1/whoami").then(res => {
      setUserView(res.data);
      console.log(res.data)
    });
  }

    useEffect(() => {
      whoAmI();
    }, []);

  if (userView.user.role === "ANON")
    return <div><h1>Anonym</h1></div>

  return <div>
    <Router>
      <Switch>
        <Layout>
          {MyMenu(userView.user)}
          <Route exact path={"/"} component={HomePage}></Route>
          <Route exact path={"/user/"} component={UserPage}></Route>
          <Route exact path={"/playlist/"} component={PlaylistsPage}></Route>
          <Route exact path="/playlist/:id" component={PlaylistPage}></Route>
          <Route exact path="/artist/:id" component={ArtistPage}></Route>
          <Route exact path="/album/:id" component={AlbumPage}></Route>
          <Route exact path="/owner/" component={ContentPage}/>
          <Route exact path="/search/:search" component={SearchPage}/>
        </Layout>
      </Switch>
    </Router>
  </div>
}

export default App;
