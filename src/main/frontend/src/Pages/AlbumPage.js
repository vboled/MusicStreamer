import {useEffect, useState} from "react";
import axios from "axios";
import {Content, Header} from "antd/es/layout/layout";
import {
    Button,
    DatePicker,
    Divider,
    Dropdown,
    Form,
    Image,
    Input,
    Layout,
    List,
    Menu,
    Select,
    Space,
    Tooltip
} from "antd";
import {Link, useHistory} from "react-router-dom";
import {
    CaretRightOutlined,
    CloseOutlined,
    DashOutlined,
    DownOutlined,
    EditOutlined, EllipsisOutlined, HeartFilled, HeartOutlined,
    PlusCircleOutlined
} from "@ant-design/icons";
import SongList from "../Elements/SongList";
import "../App.css"
import 'antd/dist/antd.css';
import Modal from "antd/es/modal/Modal";
import MyHeader from "../Elements/Header";
import getCover from "../Elements/getCover";
import {Option} from "antd/es/mentions";

const { SubMenu } = Menu;

function AlbumPage({match}) {

    const [isModalVisible, setIsModalVisible] = useState(false);
    const [isEditModalVisible, setIsEditModalVisible] = useState(false);
    const [isInfoModalVisible, setIsInfoModalVisible] = useState(false);

    const showModal = () => {
        setIsModalVisible(true);
    };

    const showSongModal = (item) => {
        setEditedSong(item)
        console.log("Edited:", editedSong)
        setIsEditModalVisible(true);
    };

    const songHandleOk = () => {
        setIsEditModalVisible(false);
    };

    const songHandleCancel = () => {
        setIsEditModalVisible(false);
    };

    const songOnFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };

    const songOnFinish = (values) => {
        if (state !== undefined)
            fileUploadHandler()

        axios.put("http://localhost:8080/api/v1/songs/song/", {
            "title":values.name,
            "id":editedSong.id
        }).then(r=>{
                getAlbum()
            }
        )
    };

    const showSongInfoModal = (item) => {
        setEditedSong(item)
        setIsInfoModalVisible(true);
    };

    const songInfoHandleOk = () => {
        setIsInfoModalVisible(false);
    };

    const songInfoHandleCancel = () => {
        setIsInfoModalVisible(false);
    };

    const fileUploadHandler = () => {
        const fd = new FormData()
        fd.append('file', state, state.name)
        axios.put(`http://localhost:8080/api/v1/songs/audio/update/${editedSong.id}`,
            fd
        ).then(
            res => {
                console.log(res.data)
                getAlbum()
            }
        )
    }

    const handleOk = () => {
        setIsModalVisible(false);
    };

    const handleCancel = () => {
        setIsModalVisible(false);
    };

    const onFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };

    const [albumView, setAlbumView] = useState({album:{}, songs: [{song:{artist:{}, album:{}}, like:{}}]})

    const getAlbum = () => {
        axios.get("http://localhost:8080/api/v1/album/", {
            params: {id:match.params.id}
        }).then(res => {
            setAlbumView(res.data)
            console.log("songs", albumView.songs)
        })
    }

    const onFinish = (values) => {
        let data = {
            "name":values.name,
            "volume":values.volume,
            "id":match.params.id,
        }
        if (albumType[0] !== "none")
            data.type = albumType[0];
        if (relAlbumDate[0] !== "none")
            data.releaseDate = relAlbumDate[0]
        axios.put("http://localhost:8080/api/v1/album/", data)
            .then(r=>{
                getAlbum()
        }
        )
    };

    const [viewer, setViewer] = useState({user:{}})
    const [editedSong, setEditedSong] = useState({})
    let relAlbumDate = useState("none")
    let albumType = useState("none")

    let history = useHistory()

    const amIOwner = () => {
        axios.get("http://localhost:8080/api/v1/whoami").then(res => {
            setViewer(res.data)
            console.log(res.data)
        });
    }

    useEffect(() => {
        getAlbum();
        amIOwner();
    }, []);

    const deleteAlbum = () => {
        axios.delete("http://localhost:8080/api/v1/album/", {
            params: {id:match.params.id}
        }).then(r => {
            history.push("/owner/")
            console.log(r)
        })
    }

    const newDefaultName = "Song №" + albumView.songs.length

    const deleteSong = () => {
        axios.delete("http://localhost:8080/api/v1/songs/", {
            params:{
                id:editedSong.id
            }
        }).then(r => {
            setIsEditModalVisible(false);
            getAlbum()
        })
    }

    const createSong = () => {
        axios.post("http://localhost:8080/api/v1/songs/create/", {
            "title":newDefaultName,
            "ownerID":viewer.user.id,
            "album":{
                "id": match.params.id
            },
            "artist": albumView.album.artist
        }).then(r=>{
            getAlbum()
        })
    }

    function getAlbumEditButton() {
        if (albumView.album.ownerID === viewer.user.id)
            return <Space size={"large"}>
                        <Button icon={<EditOutlined/>} type="primary" onClick={showModal}>
                            Edit Album
                        </Button>
                        <Button icon={<PlusCircleOutlined />} type="primary" onClick={createSong}>
                            Add Song
                        </Button>
                </Space>
        return ;
    }

    function getSongEditButton(item) {
        if (albumView.album.ownerID === viewer.user.id) {
                return <Button icon={<EditOutlined />} type="primary" shape={"circle"} onClick={() => showSongModal(item)}/>        }
        return
    }

    function albumReleaseChange(date, dateString) {
        relAlbumDate[0] = dateString
    }

    function selectTypeHandler(value) {
        albumType[0] = value
    }

    function getEditModal() {

        return <Modal title="Edit album" visible={isModalVisible} onOk={handleOk} onCancel={handleCancel}>
            <Form
                name="basic"
                labelCol={{
                    span: 8,
                }}
                wrapperCol={{
                    span: 16,
                }}
                initialValues={{
                    remember: true,
                }}
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}
            >
                <Form.Item
                    label="Name"
                    name="name"
                    rules={[
                        {
                            message: 'Update Name',
                        },
                    ]}
                >
                    <Input placeholder={albumView.album.name}/>
                </Form.Item>

                <Form.Item
                    label="Volumes"
                    name="volume"
                    rules={[
                        {
                            message: 'Update number of disks',
                        },
                    ]}
                >
                    <Input placeholder={albumView.album.volume}/>
                </Form.Item>

                <Form.Item
                    label="Release"
                >
                    <DatePicker onChange={albumReleaseChange} />
                </Form.Item>

                <Form.Item
                    label="Release"
                >
                    <Select defaultValue="Album" style={{ width: 120 }} onChange={selectTypeHandler}>
                        <Option value="album">Album</Option>
                        <Option value="ep">EP</Option>
                        <Option value="single">Single</Option>
                    </Select>
                </Form.Item>

                <Form.Item
                    label="Type"
                    name="type"
                    rules={[
                        {
                            message: 'Update type',
                        },
                    ]}
                >
                    <Input placeholder={albumView.album.name}/>
                </Form.Item>

                <Form.Item
                    name="remember"
                    valuePropName="checked"
                    wrapperCol={{
                        offset: 8,
                        span: 16,
                    }}
                >
                </Form.Item>

                <Form.Item
                    wrapperCol={{
                        offset: 8,
                        span: 16,
                    }}
                >
                    <Space size={170}>
                        <Button icon={<CloseOutlined />} type="primary"onClick={deleteAlbum}>
                            Delete
                        </Button>
                        <Button type="primary" htmlType="submit">
                            Save
                        </Button>
                    </Space>
                </Form.Item>
            </Form>
        </Modal>
    }

    const [state, setState] = useState()

    const fileSelectedHandler = (event) => {
        setState(event.target.files[0])
    }

    function getSongEditModal() {

        return <Modal title="Edit Song" visible={isEditModalVisible} onOk={songHandleOk} onCancel={songHandleCancel}>
            <Form
                name="basic"
                labelCol={{
                    span: 8,
                }}
                wrapperCol={{
                    span: 16,
                }}
                onFinish={songOnFinish}
                onFinishFailed={songOnFinishFailed}
            >
                <Form.Item
                    label="Name"
                    name="name"
                    rules={[
                        {
                            message: 'Update Name',
                        },
                    ]}
                >
                    <Input defaultValue={editedSong.title}/>
                </Form.Item>

                <Form.Item
                    label="Upload file"
                    name="update"
                    rules={[
                        {
                            message: 'Upload file',
                        },
                    ]}
                >
                    <Input type={"file"} onChange={fileSelectedHandler}/>
                </Form.Item>

                <Form.Item
                    wrapperCol={{
                        offset: 8,
                        span: 16,
                    }}
                >
                    <Space size={170}>
                        <Button icon={<CloseOutlined />} type="primary"onClick={deleteSong}>
                            Delete
                        </Button>
                        <Button type="primary" htmlType="submit">
                            Save
                        </Button>
                    </Space>
                </Form.Item>

            </Form>
        </Modal>
    }

    function getSongInfoModal() {

        return <Modal title="Song Info" visible={isInfoModalVisible} onOk={songInfoHandleOk} onCancel={songInfoHandleCancel}>
            <Menu>
                <Menu.Item>Show info</Menu.Item>
                <Menu.Item>Add to favourite</Menu.Item>
                <SubMenu title="Add to playlist">
                    <List
                        size="small"
                        bordered
                        rowKey
                        dataSource={viewer.playlistLists}
                        renderItem={(item) => {
                            if (item.main)
                                return
                            return <List.Item>
                                <Button type="text" onClick={() => addToPlaylist(item)}>{item.name}</Button>
                            </List.Item>
                        }}
                    />
                </SubMenu>
            </Menu>
        </Modal>
    }

    const addToPlaylist = (playlist) => {
        console.log(editedSong)
        axios.put("http://localhost:8080/api/v1/playlist/add/", {},{
            params:{
                songID:editedSong.id,
                playlistID:playlist.id
            }
        }).then()
    }

    const getLike = (like) => {
        if (like === null)
            return <HeartOutlined />
        return <HeartFilled />
    }

    const setLike = (songView) => {
        if (songView.like === null) {
            axios.put("http://localhost:8080/api/v1/playlist/add/main/", {},{
                params:{
                    songID:songView.song.id
                }
            }).then(r=>{
                getAlbum()
            })
        } else {
            axios.delete("http://localhost:8080/api/v1/playlist/song/main/", {
                params:{
                    songId:songView.song.id
                }
            }).then(r=>{
                getAlbum()
            })
        }
        console.log(songView)
    }

    return <Layout>
        {MyHeader()}
        <Content style={{ margin: '24px 16px 0' }}>
            <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
                <Space size={200}>
                    {getCover(albumView.album.uuid, 400, 'playlistDefault.png')}
                    <List>
                        <List.Item>
                            <p>{albumView.album.type}</p>
                        </List.Item>
                        <List.Item>
                            <h1 style={{fontSize:"40px"}}>{albumView.album.name}</h1>
                        </List.Item>
                        <List.Item>
                            <h1>{albumView.songs.length} tracks</h1>
                        </List.Item>
                        <List.Item>
                            <h1>Length</h1>
                        </List.Item>
                        <List.Item>
                            <p>{albumView.album.releaseDate.split("-")[0]}</p>
                        </List.Item>
                        <List.Item>
                            <p>"Artists"</p>
                        </List.Item>
                        <List.Item>
                            {getAlbumEditButton()}
                        </List.Item>
                    </List>
                </Space>
                {getEditModal()}
            </div>
            {getSongInfoModal()}
            {getSongEditModal()}
            <Divider orientation="left">Songs:</Divider>
            <List
                size="small"
                bordered
                rowKey
                dataSource={albumView.songs}
                renderItem={(item, index) =>
                    <List.Item>
                        <Space>
                            {index + 1}
                            <Tooltip title="Play">
                                <Button type="primary" shape="circle" icon={<CaretRightOutlined />} />
                            </Tooltip>
                            {item.song.title}
                            <Link to={`/artist/${item.song.artist.id}`}>
                                {item.song.artist.name}
                            </Link>
                            <Link to={`/album/${item.song.album.id}`}>
                                {item.song.album.name}
                            </Link>
                            <Tooltip title="Like">
                                <Button type="primary" shape="circle" onClick={() => setLike(item)}>
                                    {getLike(item.like)}
                                </Button>
                            </Tooltip>
                            {getSongEditButton(item.song)}
                            <Button icon={<EllipsisOutlined />} type="primary" shape={"circle"} onClick={() => showSongInfoModal(item.song)}/>
                        </Space>
                    </List.Item>}
            />
        </Content>
    </Layout>
}

export default AlbumPage