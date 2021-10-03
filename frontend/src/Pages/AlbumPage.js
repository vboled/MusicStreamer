import React, {useEffect, useState} from "react";
import axios from "axios";
import {Content} from "antd/es/layout/layout";
import {Button, DatePicker, Form, Input, List, Menu, Select, Space} from "antd";
import {useHistory} from "react-router-dom";
import {CloseOutlined, EditOutlined, PlusCircleOutlined} from "@ant-design/icons";
import "../App.css"
import 'antd/dist/antd.css';
import Modal from "antd/es/modal/Modal";
import getCover from "../Elements/getCover";
import {Option} from "antd/es/mentions";
import SongsList from "../Elements/SongsList";

function AlbumPage(props) {

    const [isModalVisible, setIsModalVisible] = useState(false);

    const showModal = () => {
        setIsModalVisible(true);
    };

    const handleOk = () => {
        setIsModalVisible(false);
    };

    const handleCancel = () => {
        setIsModalVisible(false);
    };

    const onFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };

    const [albumView, setAlbumView] = useState({album:{}, songs: [{song:{artist:{}, album:{genre:{}}}, like:{}}]})
    const [children, setChildren] = useState([])
    const getAlbum = () => {
        axios.get("http://localhost:8080/api/v1/album/", {
            withCredentials:true,
            params: {id:props.match.params.id}
        }).then(res => {
            setAlbumView(res.data)
            console.log("album", res.data)
            const h = [];
            for (let i = 0; i < res.data.genres.length; i++) {
                h.push(<Option key={i}>{res.data.genres[i].name}</Option>);
            }
            setChildren(h)
        })
    }

    const fileUploadHandler = () => {
        const fd = new FormData()
        fd.append('file', state, state.name)
        fd.append('id', props.match.params.id)
        axios.put(`http://localhost:8080/api/v1/album/cover/`,
            fd, {withCredentials:true}
        ).then(
            res => {
                getAlbum()
            }
        )
    }

    const onFinish = (values) => {
        if (state !== undefined)
            fileUploadHandler()

        let data = {
            "name":values.name,
            "volume":values.volume,
            "id":props.match.params.id,
        }
        if (albumType[0] !== "none")
            data.type = albumType[0];
        if (values.genre != undefined)
            data.genre = {id:parseInt(values.genre, 10) + 1}
        if (relAlbumDate[0] !== "none")
            data.releaseDate = relAlbumDate[0]
        axios.put("http://localhost:8080/api/v1/album/", data, {withCredentials:true})
            .then(r=>{
                getAlbum()
        }
        )
    };

    let relAlbumDate = useState("none")
    let albumType = useState("none")

    let history = useHistory()

    useEffect(() => {
        getAlbum();
    }, []);

    const deleteAlbum = () => {
        axios.delete("http://localhost:8080/api/v1/album/", {
            withCredentials:true,
            params: {id:props.match.params.id}
        }).then(r => {
            history.push("/owner/")
            console.log(r)
        })
    }

    const newDefaultName = "Song №" + albumView.songs.length

    const createSong = () => {
        axios.post("http://localhost:8080/api/v1/songs/create/", {
            "title":newDefaultName,
            "ownerID":props.userView.user.id,
            "album":{
                "id": props.match.params.id
            },
            "artist": albumView.album.artist
        }, {withCredentials:true}).then(r=>{
            getAlbum()
        })
    }

    function getAlbumEditButton() {
        if (albumView.album.ownerID === props.userView.user.id)
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

    function albumReleaseChange(date, dateString) {
        relAlbumDate[0] = dateString
    }

    const [state, setState] = useState()
    const [genreID, setGenreID] = useState(null)
    const fileSelectedHandler = (event) => {
        setState(event.target.files[0])
    }

    function selectTypeHandler(value) {
        albumType[0] = value
    }

    function handleChange(value) {
        setGenreID(albumView.genres[value].id)
        console.log(`selected ${albumView.genres[value].id}`);
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
                labelAlign={"left"}
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
                    label="Release date"
                >
                    <DatePicker onChange={albumReleaseChange} />
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
                    <Select defaultValue="Album" style={{ width: 120 }} onChange={selectTypeHandler}>
                        <Option value="album">Album</Option>
                        <Option value="ep">EP</Option>
                        <Option value="single">Single</Option>
                    </Select>
                </Form.Item>

                <Form.Item
                    label="Genre"
                    name="genre"
                >
                    <Select defaultValue="Choose genre" onChange={handleChange}>
                        {children}
                    </Select>
                </Form.Item>

                <Form.Item
                    label="Update Cover"
                    name="update"
                    rules={[
                        {
                            message: 'Update Cover',
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
                        <Button icon={<CloseOutlined />} type="primary" onClick={deleteAlbum}>
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

    function getLength () {
        let length = 0;
        albumView.songs.forEach(song => {
            length += song.song.duration
        })
        if (length < 3600)
            return Math.trunc(length / 60) + "min. " + Math.trunc(length % 60) + "sec."
        return Math.trunc(length / 3600) + "h. " + Math.trunc(length / 60) + "min."
    }

    function getYear() {
        let date = albumView.album.releaseDate
        if (date === null || date === undefined)
            return undefined
        return date.split('-')[0]
    }

    function getGenre() {
        if (albumView.album.genre != null)
            return  <h1 style={{fontSize:"20px"}}>{albumView.album.genre.name}</h1>
        return null
    }
    return (<Content style={{ margin: '24px 16px 0' }}>
        <div className="site-layout-background" style={{ padding: 24, minHeight: "100vh" }}>
            <Space size={100}>
                {getCover(albumView.album.uuid, 400, 'playlistDefault.png')}
                <List>
                    <List.Item>
                        <Space size={15}>
                            <h1 style={{fontSize:"20px"}}>{albumView.album.type}</h1>
                            {getGenre()}
                        </Space>
                    </List.Item>
                    <List.Item>
                        <h1 style={{fontSize:"40px"}}>{albumView.album.name}</h1>
                    </List.Item>
                    <List.Item>
                        <h1>{albumView.songs.length} tracks</h1>
                    </List.Item>
                    <List.Item>
                        <h1>{getLength()}</h1>
                    </List.Item>
                    <List.Item>
                        <p>{getYear()}</p>
                    </List.Item>
                    <List.Item>
                        {getAlbumEditButton()}
                    </List.Item>
                </List>
            </Space>
            {getEditModal()}
            <SongsList
                updatePage={getAlbum}
                forcePlay={props.forcePlay}
                setForcePlay={props.setForcePlay}
                isPlaying={props.isPlaying}
                setIsPlaying={props.setIsPlaying}
                setSongList={props.setSongList}
                setIsActive={props.setIsActive}
                setCurrentSongIndex={props.setCurrentSongIndex}
                songList={props.songList}
                songs={albumView.songs}
                isPlaylist={false}
                setSeconds={props.setSeconds}
                playlists={props.userView.playlistLists}
                currentSongIndex={props.currentSongIndex}
                userView={props.userView}
            />
        </div>
    </Content>)
}

export default AlbumPage