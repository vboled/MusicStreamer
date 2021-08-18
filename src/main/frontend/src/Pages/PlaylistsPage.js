import {useEffect, useState} from "react";
import axios from "axios";
import {Divider, Image, Layout, List, Menu} from "antd";
import {Link} from "react-router-dom";
import {HomeOutlined, NotificationOutlined, UnorderedListOutlined, UserOutlined} from "@ant-design/icons";
import {Content, Header} from "antd/es/layout/layout";
import Sider from "antd/es/layout/Sider";

const data = [
    'Racing car sprays burning fuel into crowd.',
    'Japanese princess to wed commoner.',
    'Australian walks 100km after outback crash.',
    'Man charged over missing wedding girl.',
    'Los Angeles battles huge wildfires.',
];

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

    const getPlaylists = () => {
        axios.get("http://localhost:8080/api/v1/user/playlists/").then(res => {
            console.log(res.data)
            setPlaylists(res.data)
        })
    }

    useEffect(() => {
        getPlaylists();
    }, []);

    return <Layout>
                <Header className="site-layout-sub-header-background" style={{ padding: 0 }} />
                <Content style={{ margin: '24px 16px 0' }}>
                    <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
                        <Divider orientation="left">Playlists:</Divider>
                        <List
                            size="large"
                            bordered
                            dataSource={playlists}
                            renderItem={item =>
                                <List.Item>
                                    <Link to={`/playlist/${item.id}`}>
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