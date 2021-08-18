import {Divider, Image, Layout, List, Space} from "antd";
import axios from "axios";
import {Content, Header} from "antd/es/layout/layout";
import {Link} from "react-router-dom";

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

function ArtistPage({match}) {
    // let playlistId = match.params.id;
    //
    // const [playlistDto, setPlaylistDto] = useState({playlist:{}, songs: []})
    //
    // const getPlaylist = () => {
    //     axios.get("http://localhost:8080/api/v1/playlist/", {
    //         params: {id:playlistId}
    //     }).then(res => {
    //         console.log(res.data)
    //         setPlaylistDto(res.data)
    //     })
    // }
    //
    // useEffect(() => {
    //     getPlaylist();
    // }, []);

    return <Layout>
        <Header className="site-layout-sub-header-background" style={{ padding: 0 }} />
        <Content style={{ margin: '24px 16px 0' }}>
            <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
                ArtistPage #{match.params.id}
                <Divider orientation="left">Albums:</Divider>
                <List
                    size="large"
                    bordered
                    // dataSource={playlists}
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

export default ArtistPage