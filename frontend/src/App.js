import './App.css';
import React, {useState, useEffect} from 'react';
import axios from 'axios';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import PlaylistsPage from "./Pages/PlaylistsPage";
import UserPage from "./Pages/UserPage";
import HomePage from "./Pages/HomePage";
import PlaylistPage from "./Pages/PlaylistPage";
import {Layout, Button} from "antd";
import ArtistPage from "./Pages/ArtistPage";
import AlbumPage from "./Pages/AlbumPage";
import ContentPage from "./Pages/ContentPage";
import SearchPage from "./Pages/SearchPage";
import MyMenu from "./Elements/Menu";
import MyHeader from "./Elements/Header";
import Player from "./Elements/Player";

function App() {

    const [userView, setUserView] = useState({user:{}, playlistLists:[]})
    const [songList, setSongList] = useState([{song:{album:{}, artist:{}}, playlist:{}}])
    const [currentSongIndex, setCurrentSongIndex] = useState(0);
    const [nextSongIndex, setNextSongIndex] = useState(currentSongIndex + 1);
    const [isPlaying, setIsPlaying] = useState(false)
    const [isActive, setIsActive] = useState(false)

    const whoAmI = () => {
        axios.post("http://localhost:8080/login", {
            password:"owner",
            username:"owner"
        }).then(res => {
            setUserView(res.data);
            console.log(userView.playlistLists)
        });
    }

    // useEffect(() => {
    //     whoAmI();
    // }, [])
    //
    // useEffect(() => {
    //     setNextSongIndex(() => {
    //         if (currentSongIndex + 1 > songList.length - 1) {
    //             return 0;
    //         }
    //         return currentSongIndex + 1;
    //     })
    // }, [currentSongIndex]);

    return (
        <div>
            <Button onClick={whoAmI}>
                123
            </Button>
            {/*<Router>*/}
            {/*    <Switch>*/}
            {/*        <Layout>*/}
            {/*            <MyMenu userView={userView} />*/}
            {/*            <Layout>*/}
            {/*                <MyHeader/>*/}
            {/*                <Route exact path={"/"}><HomePage user={userView.user}/></Route>*/}
            {/*                <Route exact path="/user/:id" render={(props) => <UserPage {...props} userView={userView}/>}></Route>*/}
            {/*                <Route exact path={"/playlist/"} >*/}
            {/*                    <PlaylistsPage*/}
            {/*                        whoAmI={whoAmI}/>*/}
            {/*                </Route>*/}
            {/*                <Route exact path="/playlist/:id" render={(props) => <PlaylistPage {...props}*/}
            {/*                   userView={userView}*/}
            {/*                   setSongList={setSongList}*/}
            {/*                   songList={songList}*/}
            {/*                   setCurrentSongIndex={setCurrentSongIndex}*/}
            {/*                   currentSongIndex={currentSongIndex}*/}
            {/*                   isPlaying={isPlaying}*/}
            {/*                   setIsPlaying={setIsPlaying}*/}
            {/*                   setIsActive={setIsActive}*/}
            {/*                   whoAmI={whoAmI}*/}
            {/*                />}*/}
            {/*                ></Route>*/}
            {/*                <Route exact path="/artist/:id" render={(props) => <ArtistPage {...props}*/}
            {/*                   userView={userView}*/}
            {/*                   setSongList={setSongList}*/}
            {/*                   songList={songList}*/}
            {/*                   setCurrentSongIndex={setCurrentSongIndex}*/}
            {/*                   currentSongIndex={currentSongIndex}*/}
            {/*                   isPlaying={isPlaying}*/}
            {/*                   setIsPlaying={setIsPlaying}*/}
            {/*                   setIsActive={setIsActive}*/}
            {/*                   whoAmI={whoAmI}*/}
            {/*                />}></Route>*/}
            {/*                <Route exact path="/album/:id" render={(props) => <AlbumPage {...props}*/}
            {/*                     userView={userView}*/}
            {/*                     setSongList={setSongList}*/}
            {/*                     songList={songList}*/}
            {/*                     setCurrentSongIndex={setCurrentSongIndex}*/}
            {/*                     currentSongIndex={currentSongIndex}*/}
            {/*                     isPlaying={isPlaying}*/}
            {/*                     setIsPlaying={setIsPlaying}*/}
            {/*                     setIsActive={setIsActive}*/}
            {/*                     whoAmI={whoAmI}*/}
            {/*                />}>*/}
            {/*                </Route>*/}
            {/*                <Route exact path="/owner/" component={ContentPage}/>*/}
            {/*                <Route exact path="/search/:search" component={SearchPage}/>*/}
            {/*                <Layout>*/}
            {/*                    <Player*/}
            {/*                        songList={songList}*/}
            {/*                        currentSongIndex={currentSongIndex}*/}
            {/*                        setCurrentSongIndex={setCurrentSongIndex}*/}
            {/*                        nextSongIndex={nextSongIndex}*/}
            {/*                        isPlaying={isPlaying}*/}
            {/*                        setIsPlaying={setIsPlaying}*/}
            {/*                        isActive={isActive}*/}
            {/*                    />*/}
            {/*                </Layout>*/}
            {/*            </Layout>*/}
            {/*        </Layout>*/}
            {/*    </Switch>*/}
            {/*</Router>*/}
        </div>
    );
}

export default App;