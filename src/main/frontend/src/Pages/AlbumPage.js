import {useEffect, useState} from "react";
import axios from "axios";
import {Content, Header} from "antd/es/layout/layout";
import {Button, Divider, Image, Layout, List, Space, Tooltip} from "antd";
import {Link} from "react-router-dom";
import {CaretRightOutlined} from "@ant-design/icons";
import SongList from "../Elements/SongList";
import "../App.css"
import 'antd/dist/antd.css';

function getCover(uuid) {
    let name = uuid
    if (name === null)
        name = 'playlistDefault.png'
    return <Image
        width={400}
        preview={false}
        src={window.location.origin + '/img/' + name}
    />
}

function AlbumPage({match}) {

    const [albumView, setAlbumView] = useState({album:{}, songs: []})

    const getPlaylist = () => {
        axios.get("http://localhost:8080/api/v1/album/", {
            params: {id:match.params.id}
        }).then(res => {
            console.log(res.data)
            setAlbumView(res.data)
        })
    }

    useEffect(() => {
        getPlaylist();
    }, []);

    return <Layout>
        <Header className="site-layout-sub-header-background" style={{ padding: 0 }} />
        <Content style={{ margin: '24px 16px 0' }}>
            <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
                <Image.PreviewGroup>
                    {getCover(albumView.album.uuid)}
                    <h1>{albumView.album.name}</h1>
                    <h1>{albumView.songs.length} tracks</h1>
                    <h1>Length</h1>
                    <p>{albumView.album.releaseDate}</p>
                </Image.PreviewGroup>
            </div>
            {SongList(albumView.songs)}
        </Content>
    </Layout>
}

export default AlbumPage