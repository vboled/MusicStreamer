import {Button, Image, Layout} from 'antd';
import React, {useEffect, useState} from 'react'
import "../App.css"
import 'antd/dist/antd.css';
import axios from "axios";
import SongsList from "../Elements/SongsList";

const {Content} = Layout;

function HomePage(props) {

    const getRecommendations = () => {
        axios.get("http://localhost:8080/api/v1/user/recommendations/", {withCredentials:true}).then(res => {
            props.setRecView(res.data)
        })
    }

    useEffect(() => {
        getRecommendations()
    }, [])

    return (<Content style={{ margin: '24px 16px 0' }}>
                <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
                    <h1>Recommendations:</h1>
                    <SongsList
                        forcePlay={props.forcePlay}
                        setForcePlay={props.setForcePlay}
                        updatePage={getRecommendations}
                        isPlaying={props.isPlaying}
                        setIsPlaying={props.setIsPlaying}
                        setSongList={props.setSongList}
                        setIsActive={props.setIsActive}
                        setCurrentSongIndex={props.setCurrentSongIndex}
                        songList={props.songList}
                        songs={props.recView}
                        isPlaylist={false}
                        setSeconds={props.setSeconds}
                        playlists={props.userView.playlistLists}
                        currentSongIndex={props.currentSongIndex}
                        userView={props.userView}
                    />
                </div>
            </Content>)

}

export default HomePage;