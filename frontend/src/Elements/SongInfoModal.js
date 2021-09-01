import Modal from "antd/es/modal/Modal";
import {Button, List, Menu} from "antd";
import axios from "axios";
import React from 'react'

const { SubMenu } = Menu;

function SongInfoModal(props) {

    const addToPlaylist = (playlist) => {
        axios.put("http://localhost:8080/api/v1/playlist/add/", {},{
            params:{
                songID:props.editedSong.id,
                playlistID:playlist.id
            }
        }).then()
    }

    return <Modal title="Song Info" visible={props.isInfoModalVisible} onOk={props.songInfoHandleOk} onCancel={props.songInfoHandleCancel}>
        <Menu>
            <Menu.Item>Show info</Menu.Item>
            <Menu.Item>Add to favourite</Menu.Item>
            <SubMenu title="Add to playlist">
                <List
                    size="small"
                    bordered
                    rowKey
                    dataSource={props.playlists}
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

export default SongInfoModal