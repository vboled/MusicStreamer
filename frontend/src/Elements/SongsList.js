import {Button, Divider, List, Space, Tooltip} from "antd";
import {
    CaretRightOutlined,
    CloseOutlined, EditOutlined,
    EllipsisOutlined,
    HeartFilled,
    HeartOutlined,
    PauseOutlined
} from "@ant-design/icons";
import {Link} from "react-router-dom";
import React, {useState} from "react";
import axios from "axios";
import SongInfoModal from "./SongInfoModal";
import Like from "./Like";
import SongEditModal from "./SongEditModal";

function SongsList (props) {

    const [editedSong, setEditedSong] = useState({})

    const [isInfoModalVisible, setIsInfoModalVisible] = useState(false);

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

    const [isEditModalVisible, setIsEditModalVisible] = useState(false);

    const showSongModal = (item) => {
        setEditedSong(item)
        setIsEditModalVisible(true);
    };

    const showEditInfoModal = (item) => {
        setEditedSong(item)
        setIsEditModalVisible(true);
    };

    const songEditHandleOk = () => {
        setIsEditModalVisible(false);
    };

    const songEditHandleCancel = () => {
        setIsEditModalVisible(false);
    };

    const deleteSong = (song) => {
        axios.delete("http://localhost:8080/api/v1/playlist/song/", {
            params:{
                addedSongId:song.id
            }
        }).then(
            r=>props.updatePage()
        )
    }

    const playSong = (index) => {
        if (props.isPlaying) {
            props.setIsPlaying(false)
        } else {
            props.setIsActive(true)
            props.setIsPlaying(true)
            props.setSongList(props.songs)
            props.setCurrentSongIndex(index)
        }
    }

    function SongEditButton(props) {
        if (props.item.ownerID === props.user.id) {
            return <Button icon={<EditOutlined />} type="primary" shape={"circle"} onClick={() => showSongModal(props.item)}/>        }
        return <div/>
    }

    function RemoveButton(props) {
        if (props.isPlaylist === true)
            return <Tooltip title="Remove">
                <Button type="primary" size={"small"} shape="circle" icon={<CloseOutlined />} onClick={() => deleteSong(props.item)}/>
            </Tooltip>
        return <div/>
    }

    return (
        <div>
            <SongInfoModal
                isInfoModalVisible={isInfoModalVisible}
                songInfoHandleOk={songInfoHandleOk}
                songInfoHandleCancel={songInfoHandleCancel}
                playlists={props.playlists}
                editedSong={editedSong}
            />
            <SongEditModal
                isEditModalVisible={isEditModalVisible}
                songEditHandleOk={songEditHandleOk}
                songEditHandleCancel={songEditHandleCancel}
                playlists={props.playlists}
                editedSong={editedSong}
                setIsEditModalVisible={setIsEditModalVisible}
                updatePage={props.updatePage}
            />
            <Divider orientation="left">Songs:</Divider>
            <List
                size="small"
                bordered
                rowKey
                dataSource={props.songs}
                renderItem={(item, index) => {
                    let playIcon = <CaretRightOutlined />
                    let disabled = false
                    console.log(props);
                    if (item.song.uuid === null) {
                        disabled = true
                    }
                    else if (item.song.id === props.songList[props.currentSongIndex].song.id) {
                        if (!props.isPlaying) {
                            playIcon = <CaretRightOutlined />
                        } else {
                            playIcon = <PauseOutlined />
                        }
                    }
                    return <List.Item>
                        <Space>
                            {index + 1}
                            <Tooltip title="Play">
                                <Button disabled={disabled} type="primary" shape="circle" icon={playIcon}
                                        onClick={() => playSong(index)}/>
                            </Tooltip>
                            {item.song.title}
                            <Link to={`/artist/${item.song.artist.id}`}>
                                {item.song.artist.name}
                            </Link>
                            <Link to={`/album/${item.song.album.id}`}>
                                {item.song.album.name}
                            </Link>
                            <Like
                                item={item}
                                updatePage={props.updatePage}/>
                            <Button icon={<EllipsisOutlined />} type="primary" shape={"circle"} onClick={() => showSongInfoModal(item.song)}/>
                            <SongEditButton
                                item={item.song}
                                user={props.userView.user}/>
                            <RemoveButton
                                isPlaylist={props.isPlaylist}
                                item={item}/>
                        </Space>
                    </List.Item>
                }
                }
            />
        </div>
    )
}

export default SongsList