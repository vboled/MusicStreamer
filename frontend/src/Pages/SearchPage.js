import {Content} from "antd/es/layout/layout";
import React, {useEffect, useState} from "react";
import axios from "axios";
import AlbumList from "../Elements/AlbumList";
import ArtistList from "../Elements/ArtistList";
import SongsList from "../Elements/SongsList";

function SearchPage(props) {

    const [searchView, setSearchView] = useState({songs:[], albums:[], artists:[]})

    const getSearch = () => {
        axios.get("http://localhost:8080/api/v1/search/", {
            withCredentials:true,
            params: {search:props.match.params.search}
        }).then(res => {
            setSearchView(res.data)
            console.log("res", res.data)
        })
    }

    useEffect(() => {
        getSearch();
    }, [props.match.params.search]);

    return (<Content style={{ margin: '24px 16px 0' }}>
            <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
                <SongsList
                    updatePage={getSearch}
                    isPlaying={props.isPlaying}
                    setIsPlaying={props.setIsPlaying}
                    setSongList={props.setSongList}
                    setIsActive={props.setIsActive}
                    setCurrentSongIndex={props.setCurrentSongIndex}
                    songList={props.songList}
                    songs={searchView.songs}
                    isPlaylist={false}
                    setSeconds={props.setSeconds}
                    playlists={props.userView.playlistLists}
                    currentSongIndex={props.currentSongIndex}
                    userView={props.userView}
                />
                {AlbumList(searchView.albums)}
                {ArtistList(searchView.artists)}
            </div>
        </Content>)
}

export default SearchPage