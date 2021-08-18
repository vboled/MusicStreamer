import {Button, Divider, Image, Layout, List, Space, Tooltip} from "antd";
import axios from "axios";
import {Content, Header} from "antd/es/layout/layout";
import {Link} from "react-router-dom";
import {useEffect, useState} from "react";
import {CaretRightOutlined} from "@ant-design/icons";

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

function ArtistPage({match}) {

    const [artistView, setArtistView] = useState({artist:{}, songs: [], albums: []})

    const getArtist = () => {
        axios.get("http://localhost:8080/api/v1/artist/", {
            params: {id:match.params.id}
        }).then(res => {
            console.log(res.data)
            setArtistView(res.data)
        })
    }

    useEffect(() => {
        getArtist();
    }, []);

    return <Layout>
        <Header className="site-layout-sub-header-background" style={{ padding: 0 }} />
        <Content style={{ margin: '24px 16px 0' }}>
            <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
                ArtistPage #{match.params.id}
                <Divider orientation="left">Albums:</Divider>
                <List
                    size="large"
                    bordered
                    dataSource={artistView.albums}
                    renderItem={item =>
                        <List.Item>
                            <Link to={`/album/${item.id}`}>
                                {getCover(item.uuid)}
                                <h1>{item.name}</h1>
                            </Link>
                            <br/>
                        </List.Item>}
                />
                <Divider orientation="left">Songs:</Divider>
                <List
                    size="small"
                    bordered
                    dataSource={artistView.songs}
                    renderItem={item =>
                        <List.Item>
                            <Space>
                                <Tooltip title="Play">
                                    <Button type="primary" shape="circle" icon={<CaretRightOutlined />} />
                                </Tooltip>
                                    {item.title}
                                    <Link to={`/artist/${item.artist.id}`}>
                                        {item.artist.name}
                                    </Link>
                                    {item.album.name}
                            </Space>
                        </List.Item>}
                />
            </div>
        </Content>
    </Layout>
}

export default ArtistPage