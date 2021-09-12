import {Button, Tooltip} from "antd";
import axios from "axios";
import {HeartFilled, HeartOutlined} from "@ant-design/icons";
import React from 'react'

function Like(props) {

    const setLike = (songView) => {
        if (songView.like === null) {
            axios.put("http://localhost:8080/api/v1/playlist/add/main/", {},{
                withCredentials:true,
                params:{
                    songID:songView.song.id
                }
            }).then(r=>{
                props.updatePage()
            })
        } else {
            axios.delete("http://localhost:8080/api/v1/playlist/song/main/", {
                withCredentials:true,
                params:{
                    songId:songView.song.id
                }
            }).then(r=>{
                props.updatePage()
            })
        }
        console.log(songView)
    }

    const getLike = (like) => {
        if (like === null)
            return <HeartOutlined />
        return <HeartFilled />
    }


    return (<Tooltip title="Like">
        <Button type="primary" shape="circle" onClick={() => setLike(props.item)}>
            {getLike(props.item.like)}
        </Button>
    </Tooltip>)
}

export default Like