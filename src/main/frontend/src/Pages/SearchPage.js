import {Button, Divider, Empty, Form, Input, Layout, List, Space} from "antd";
import {Content, Header} from "antd/es/layout/layout";
import {useEffect, useState} from "react";
import axios from "axios";
import MyHeader from "../Elements/Header";
import SongList from "../Elements/SongList";
import {Link} from "react-router-dom";
import getCover from "../Elements/getCover";
import AlbumList from "../Elements/AlbumList";
import ArtistList from "../Elements/ArtistList";


function SearchPage({match}) {

    const [searchView, setSearchView] = useState({songs:[], albums:[], artists:[]})

    const getSearch = () => {
        axios.get("http://localhost:8080/api/v1/search/", {
            params: {search:match.params.search}
        }).then(res => {
            setSearchView(res.data)
            console.log("res", searchView.albums)
        })
    }

    useEffect(() => {
        getSearch();
    }, []);

    return (<Content style={{ margin: '24px 16px 0' }}>
            <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
                {SongList(searchView.songs)}
                {AlbumList(searchView.albums)}
                {ArtistList(searchView.artists)}
            </div>
        </Content>)
}

export default SearchPage