import {Divider, Image, Layout, List} from "antd";
import {Content, Header} from "antd/es/layout/layout";
import "../App.css"
import 'antd/dist/antd.css';
import axios from "axios";
import {useEffect, useState} from "react";
import {Link} from "react-router-dom";

function getCover(uuid, w) {
    let name = uuid
    if (name === null)
        name = 'playlistDefault.png'
    return <Image
        width={w}
        preview={false}
        src={window.location.origin + '/img/' + name}
    />
}

function ContentPage() {

    const [contentView, setContentView] = useState({artists:[], albums: []})

    const getArtist = () => {
        axios.get("http://localhost:8080/api/v1/user/content/").then(res => {
            console.log(res.data)
            setContentView(res.data)
        })
    }

    useEffect(() => {
        getArtist();
    }, []);


    return <Layout>
        <Header className="site-layout-sub-header-background" style={{ padding: 0 }} />
        <Content style={{ margin: '24px 16px 0' }}>
            <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
                <Divider orientation="left">Albums:</Divider>
                <List
                    size="large"
                    bordered
                    dataSource={contentView.albums}
                    renderItem={item =>
                        <List.Item>
                            <Link to={`/album/id/${item.id}`}>
                                {getCover(item.uuid, 200)}
                                <h1>{item.name}</h1>
                            </Link>
                            <br/>
                        </List.Item>}
                />
                <Divider orientation="left">Artists:</Divider>
                <List
                    size="large"
                    bordered
                    dataSource={contentView.artists}
                    renderItem={item =>
                        <List.Item>
                            <Link to={`/artist/id/${item.id}`}>
                                {getCover(item.uuid, 200)}
                                <h1>{item.name}</h1>
                            </Link>
                            <br/>
                        </List.Item>}
                />
            </div>
        </Content>
    </Layout>
}

export default ContentPage