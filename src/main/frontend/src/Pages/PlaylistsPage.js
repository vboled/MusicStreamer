import {useEffect, useState} from "react";
import axios from "axios";
import {Button, Divider, Image, Layout, List, Menu, Space, Tooltip} from "antd";
import {Link, Redirect, useHistory} from "react-router-dom";
import {HomeOutlined, NotificationOutlined, PlusOutlined, UnorderedListOutlined, UserOutlined} from "@ant-design/icons";
import {Content, Header} from "antd/es/layout/layout";
import Sider from "antd/es/layout/Sider";
import "../App.css"
import 'antd/dist/antd.css';
import MyHeader from "../Elements/Header";

function getCover(uuid, main) {
    let name = uuid
    if (name === null)
        name = 'playlistDefault.png'
    if (main)
        name = 'favouritePlaylist.png'
    return <Image
        width={200}
        preview={false}
        src={window.location.origin + '/img/' + name}
    />
}

function PlaylistsPage(){
    const [playlists, setPlaylists] = useState([])

    const getPlaylists = () => {
        axios.get("http://localhost:8080/api/v1/user/playlists/").then(res => {
            console.log("get", res.data)
            setPlaylists(res.data)
        })
    }

    useEffect(() => {
        getPlaylists();
    }, []);

    const newDefaultName = "Playlist №" + playlists.length

    const createPlaylist = () => {
        axios.post("http://localhost:8080/api/v1/playlist/", {
            name:newDefaultName
        }).then(r => {
            getPlaylists()
        })
    }

    return (<Content style={{ margin: '24px 16px 0' }}>
                    <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
                        <Divider orientation="left">Playlists:</Divider>
                        <List
                            size="large"
                            bordered
                            header={<Tooltip title="create">
                                <Button onClick={createPlaylist} type="primary" shape="square" icon={<PlusOutlined />} >
                                    Create new playlist
                                </Button>
                            </Tooltip>}
                            dataSource={playlists}
                            renderItem={item =>
                                <List.Item>
                                    <Space size={100}>
                                        <Link to={`/playlist/${item.id}`}>
                                            {getCover(item.uuid, item.main)}
                                        </Link>
                                        <Link to={`/playlist/${item.id}`}>
                                            <h1>{item.name}</h1>
                                        </Link>
                                    </Space>
                                </List.Item>}
                        />
                    </div>
                </Content>)

}

export default PlaylistsPage;