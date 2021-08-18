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

// const WhoAmI = () => {
//
//   const [result, setResult] = useState({user:{}, playlistLists: []});
//
//   const getStatus = () => {
//     axios.get("http://localhost:8080/api/v1/whoami").then(res => {
//       console.log(res.data);
//       console.log(res.data);
//       setResult(res.data);
//     });
//   }
//
//   useEffect(() => {
//     getStatus();
//   }, []);
//
//   return SignedPage(result)
// }

// function App() {
//   return (
//     <Router>
//       <div className="App">
//       <Switch>
//         <Route exact path="/" component={WhoAmI}></Route>
//         <Route exact path="/playlist/:id" component={PlaylistPage}></Route>
//         <Route exact path="/user/:username" component={UserPage}></Route>
//       </Switch>
//       </div>
//     </Router>
//   );
// }

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
  if (result.user === {})
    return "Anonym"
  return result
}

function App() {
  let res = WhoAmI();
  if (res === "Anonym")
    return <div><h1>Anonym</h1></div>
  return <div>
    <Router>
      <Switch>
        <Layout>
          <MyMenu/>
          <Route exact path={"/"} component={HomePage}></Route>
          <Route exact path={"/user/"} component={UserPage}></Route>
          <Route exact path={"/playlist/"} component={PlaylistsPage}></Route>
          <Route exact path="/playlist/:id" component={PlaylistPage}></Route>
          <Route exact path="/artist/:id" component={ArtistPage}></Route>
        </Layout>
      </Switch>
    </Router>
  </div>
}

export default App;
