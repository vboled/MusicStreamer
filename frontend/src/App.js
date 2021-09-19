import './App.css';
import React, {useState, useEffect} from 'react';
import axios from 'axios';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import PlaylistsPage from "./Pages/PlaylistsPage";
import UserPage from "./Pages/UserPage";
import HomePage from "./Pages/HomePage";
import PlaylistPage from "./Pages/PlaylistPage";
import {Layout, Button, Space, List} from "antd";
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
    const [seconds, setSeconds] = useState(-1)
    const [listeningID, setListeningID] = useState(-1)
    const [isActive, setIsActive] = useState(false)
    const [isAuth, setIsAuth] = useState(false)

    const whoAmI = () => {
        axios.get("http://localhost:8080/api/v1/whoami/",
            {withCredentials:true}).then(res => {
            setUserView(res.data)
            console.log(userView)
            setIsAuth(true)
        }).catch(err => {
            setIsAuth(false)
        });
    }

    useEffect(() => {
        whoAmI();
    }, [])

    useEffect(() => {
        setNextSongIndex(() => {
            if (currentSongIndex + 1 > songList.length - 1) {
                return 0;
            }
            return currentSongIndex + 1;
        })
    }, [currentSongIndex]);

    if (!isAuth)
        return <LoginPage
            setIsAuth={setIsAuth}
            whoAmI={whoAmI}
        />
    return (
        <div>
            <Router>
                <Switch>
                    <Layout>
                        <MyMenu userView={userView} />
                        <Layout>
                            <MyHeader/>
                            <Route exact path={"/"}><HomePage user={userView.user}/></Route>
                            <Route exact path="/user/:id" render={(props) => <UserPage {...props} userView={userView}/>}></Route>
                            <Route exact path={"/playlist/"} >
                                <PlaylistsPage
                                    whoAmI={whoAmI}/>
                            </Route>
                            <Route exact path="/playlist/:id" render={(props) => <PlaylistPage {...props}
                               userView={userView}
                               setSongList={setSongList}
                               songList={songList}
                               setCurrentSongIndex={setCurrentSongIndex}
                               currentSongIndex={currentSongIndex}
                               isPlaying={isPlaying}
                               setIsPlaying={setIsPlaying}
                               setIsActive={setIsActive}
                               seconds={seconds}
                               setSeconds={setSeconds}
                               whoAmI={whoAmI}
                            />}
                            ></Route>
                            <Route exact path="/artist/:id" render={(props) => <ArtistPage {...props}
                               userView={userView}
                               setSongList={setSongList}
                               songList={songList}
                               setCurrentSongIndex={setCurrentSongIndex}
                               currentSongIndex={currentSongIndex}
                               isPlaying={isPlaying}
                               seconds={seconds}
                               setSeconds={setSeconds}
                               setIsPlaying={setIsPlaying}
                               setIsActive={setIsActive}
                               whoAmI={whoAmI}
                            />}></Route>
                            <Route exact path="/album/:id" render={(props) => <AlbumPage {...props}
                                 userView={userView}
                                 setSongList={setSongList}
                                 songList={songList}
                                 setCurrentSongIndex={setCurrentSongIndex}
                                 currentSongIndex={currentSongIndex}
                                 isPlaying={isPlaying}
                                 seconds={seconds}
                                 setSeconds={setSeconds}
                                 setIsPlaying={setIsPlaying}
                                 setIsActive={setIsActive}
                                 whoAmI={whoAmI}
                            />}>
                            </Route>
                            <Route exact path="/owner/" component={ContentPage}/>
                            <Route exact path="/search/:search" component={SearchPage}/>
                            <Content style={{ margin: '0px 16px 0' }}>
                                <div className="site-layout-background" style={{ padding: 24, minHeight: "170px" }}>
                                </div>
                            </Content>
                            <Layout>
                                <Player
                                    userView={userView}
                                    songList={songList}
                                    currentSongIndex={currentSongIndex}
                                    setCurrentSongIndex={setCurrentSongIndex}
                                    nextSongIndex={nextSongIndex}
                                    isPlaying={isPlaying}
                                    listeningID={listeningID}
                                    setListeningID={setListeningID}
                                    setIsPlaying={setIsPlaying}
                                    isActive={isActive}
                                    seconds={seconds}
                                    setSeconds={setSeconds}
                                />
                            </Layout>
                        </Layout>
                    </Layout>
                </Switch>
            </Router>
        </div>
    );
}

export default App;