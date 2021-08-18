import {useEffect, useState} from "react";
import axios from "axios";
import {Content, Header} from "antd/es/layout/layout";
import {Button, Divider, Image, Layout, List, Space, Tooltip} from "antd";
import {Link} from "react-router-dom";
import {CaretRightOutlined, SearchOutlined} from "@ant-design/icons";

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

function PlaylistPage({match}) {
    let playlistId = match.params.id;

    const [playlistDto, setPlaylistDto] = useState({playlist:{}, songs: []})

    const getPlaylist = () => {
        axios.get("http://localhost:8080/api/v1/playlist/", {
            params: {id:playlistId}
        }).then(res => {
            console.log(res.data)
            setPlaylistDto(res.data)
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
                    {getCover(playlistDto.playlist.uuid)}
                    <h1>{playlistDto.playlist.name}</h1>
                    <h1>{playlistDto.songs.length} tracks</h1>
                    <h1>Length</h1>
                    <p>{playlistDto.playlist.description}</p>
                </Image.PreviewGroup>
                <List
                    size="small"
                    bordered
                    dataSource={playlistDto.songs}
                    renderItem={item =>
                        <List.Item>
                            <Space>
                                <Tooltip title="Play">
                                    <Button type="primary" shape="circle" icon={<CaretRightOutlined />} />
                                </Tooltip>
                                {item.song.title}
                                <Link to={`/artist/${item.song.artist.id}`}>
                                    {item.song.artist.name}
                                </Link>
                                {item.song.album.name}
                            </Space>
                        </List.Item>}
                />
            </div>
        </Content>
    </Layout>
}

export default PlaylistPage