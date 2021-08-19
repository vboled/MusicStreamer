import {useEffect, useState} from "react";
import axios from "axios";
import {Button, Divider, Image, Layout, List, Menu, Tooltip} from "antd";
import {Link, Redirect, useHistory} from "react-router-dom";
import {HomeOutlined, NotificationOutlined, PlusOutlined, UnorderedListOutlined, UserOutlined} from "@ant-design/icons";
import {Content, Header} from "antd/es/layout/layout";
import Sider from "antd/es/layout/Sider";
import "../App.css"
import 'antd/dist/antd.css';

function getCover(uuid) {
    let name = uuid
    if (name === null)
        name = 'playlistDefault.png'
    return <Image
        width={200}
        preview={false}
        src={window.location.origin + '/img/' + name}
    />
}

function PlaylistsPage() {
    const [playlists, setPlaylists] = useState([])

    let history = useHistory();

    const getPlaylists = () => {
        axios.get("http://localhost:8080/api/v1/user/playlists/").then(res => {
            console.log(res.data)
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

        })

    }

    return <Layout>
                <Header className="site-layout-sub-header-background" style={{ padding: 0 }} />
                <Content style={{ margin: '24px 16px 0' }}>
                    <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
                        <Link to="/playlist/">
                            <Tooltip title="create">
                                <Button onClick={createPlaylist} type="primary" shape="square" icon={<PlusOutlined />} >
                                    Create new playlist
                                </Button>
                            </Tooltip>
                        </Link>
                        <Divider orientation="left">Playlists:</Divider>
                        <List
                            size="large"
                            bordered
                            dataSource={playlists}
                            renderItem={item =>
                                <List.Item>
                                    <Link to={`/playlist/id/${item.id}`}>
                                        {getCover(item.uuid)}
                                        <h1>{item.name}</h1>
                                    </Link>
                                    <br/>
                                </List.Item>}
                        />
                    </div>
                </Content>
            </Layout>

}

export default PlaylistsPage;