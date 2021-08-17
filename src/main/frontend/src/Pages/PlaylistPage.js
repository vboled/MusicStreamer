import {useEffect, useState} from "react";
import axios from "axios";
import {Layout, Menu} from "antd";
import {Link} from "react-router-dom";
import {HomeOutlined, NotificationOutlined, UnorderedListOutlined, UserOutlined} from "@ant-design/icons";
import {Content, Header} from "antd/es/layout/layout";
import Sider from "antd/es/layout/Sider";

// function PlaylistPage({match}) {
//
//     const [item, setItem] = useState({})
//
//     let playlistId = match.params.id;
//
//     const getPlaylist = () => {
//         axios.get("http://localhost:8080/api/v1/playlist/", {
//             params: {id:playlistId}
//         }).then(res => {
//             console.log(res.data)
//         })
//     }
//
//     useEffect(() => {
//         getPlaylist();
//     }, []);
//
//     return (<div>
//         <h1>Hello, Playlist № {playlistId}</h1>
//     </div>);
// }

function PlaylistPage() {
    return <Layout>
                <Header className="site-layout-sub-header-background" style={{ padding: 0 }} />
                <Content style={{ margin: '24px 16px 0' }}>
                    <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
                        Playlist Page
                    </div>
                </Content>
            </Layout>

}

export default PlaylistPage;