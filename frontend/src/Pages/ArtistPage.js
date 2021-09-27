import {Button, Divider, Form, Input, Layout, List, Space, Tooltip} from "antd";
import React, {useEffect, useState} from 'react'
import axios from "axios";
import {Content} from "antd/es/layout/layout";
import {Link, useHistory} from "react-router-dom";
import {CloseOutlined, EditOutlined, PlusOutlined} from "@ant-design/icons";
import "../App.css"
import 'antd/dist/antd.css';
import Modal from "antd/es/modal/Modal";
import getCover from "../Elements/getCover";
import SongsList from "../Elements/SongsList";
import Statistics from "../Elements/Statistics";

function ArtistPage(props) {

    const [artistView, setArtistView] = useState({artist:{}, songs: [{song:{artist:{}, album:{}}, like:{}}], albums: []})
    const [stat, setStat] = useState([{listenings:[], region:{}}])

    const getArtist = () => {
        axios.get("http://localhost:8080/api/v1/artist/", {
            withCredentials:true,
            params: {id:props.match.params.id}
        }).then(res => {
            setArtistView(res.data)
            if (res.data.artist.ownerID === props.userView.user.id)
                getStatistic(res.data.artist.id)
        })
    }

    const getStatistic = (id) => {
        axios.get("http://localhost:8080/api/v1/listening/by-artist/regions/", {
            withCredentials:true,
            params:{
                artistID:id
            }
        }).then(r => {
            setStat(r.data)
        })
    }

    useEffect(() => {
        getArtist();
    }, []);

    const fileUploadHandler = () => {
        const fd = new FormData()
        fd.append('file', state, state.name)
        fd.append('id', props.match.params.id)
        axios.put(`http://localhost:8080/api/v1/artist/cover/`,
            fd, {withCredentials:true}
        ).then(
            res => {
                getArtist()
            }
        )
    }

    const onFinish = (values) => {
        if (state !== undefined)
            fileUploadHandler()
        axios.put("http://localhost:8080/api/v1/artist/", {
            "name":values.name,
            "id":props.match.params.id,
            "ownerID":props.userView.user.id
        }, {withCredentials:true}).then(r=>{
            getArtist()
        })
    };

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

    let history = useHistory()

    const deleteArtist = () => {
        axios.delete("http://localhost:8080/api/v1/artist/", {
            withCredentials:true,
            params: {id:props.match.params.id}
        }).then(r => {
            console.log(r)
            history.push("/owner/")
        })
    }

    const newDefaultName = "Album №" + artistView.albums.length

    const getArtistEditButton = () => {
        if (artistView.artist.ownerID === props.userView.user.id)
            return <Button icon={<EditOutlined/>} type="primary" onClick={showModal}>
                Edit Artist
            </Button>
        return  <div/>
    }

    const getAlbumCreateButton = () => {
        if (artistView.artist.ownerID === props.userView.user.id)
            return <Tooltip title="create Album">
                <Button onClick={createAlbum} type="primary" shape="square" icon={<PlusOutlined />} >
                    Add Album
                </Button>
            </Tooltip>
        return  <div/>
    }

    const createAlbum = () => {
        axios.post("http://localhost:8080/api/v1/album/", {
            name:newDefaultName,
            artist:{
                id:props.match.params.id
            }
        }, {withCredentials:true}).then(
            r=>{
                getArtist()
            }
        )
    }

    const [state, setState] = useState()

    const fileSelectedHandler = (event) => {
        setState(event.target.files[0])
    }

    function getModal() {
        return <Modal title="Edit playlist" visible={isModalVisible} onOk={handleOk} onCancel={handleCancel}>
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
                    <Input placeholder={artistView.artist.name}/>
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
                        <Button icon={<CloseOutlined />} type="primary"onClick={deleteArtist}>
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

    function GetStatistics() {
        if (artistView.artist.ownerID === props.userView.user.id)
            return <Statistics
                stat={stat}
            />
        return <div/>;
    }

    return (<Content style={{ margin: '24px 16px 0' }}>
            <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
                <Space size={200}>
                    {getCover(artistView.artist.uuid, 400, 'playlistDefault.png')}
                    <List>
                        <List.Item>
                            <h1>{artistView.artist.name}</h1>
                        </List.Item>
                        <List.Item>
                            {getArtistEditButton()}
                        </List.Item>
                    </List>
                </Space>
                {getModal()}
                <Divider orientation="left">Albums:</Divider>
                <List
                    size="large"
                    bordered
                    dataSource={artistView.albums}
                    header={getAlbumCreateButton()}
                    renderItem={item =>
                        <List.Item>
                            <Space size={100}>
                                <Link to={`/album/${item.id}`}>
                                    {getCover(item.uuid, 200, 'playlistDefault.png')}
                                </Link>
                                <Link to={`/album/${item.id}`}>
                                    <h1>{item.name}</h1>
                                </Link>
                            </Space>
                        </List.Item>}
                >
                </List>
                <SongsList
                    updatePage={getArtist}
                    isPlaying={props.isPlaying}
                    setIsPlaying={props.setIsPlaying}
                    setSongList={props.setSongList}
                    setIsActive={props.setIsActive}
                    setCurrentSongIndex={props.setCurrentSongIndex}
                    songList={props.songList}
                    songs={artistView.songs}
                    isPlaylist={false}
                    setSeconds={props.setSeconds}
                    playlists={props.userView.playlistLists}
                    currentSongIndex={props.currentSongIndex}
                    userView={props.userView}
                />
                <GetStatistics/>
            </div>
        </Content>)
}

export default ArtistPage