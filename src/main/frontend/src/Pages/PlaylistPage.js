import {useEffect, useState} from "react";
import axios from "axios";

function PlaylistPage({match}) {

    const [item, setItem] = useState({})

    let playlistId = match.params.id;

    const getPlaylist = () => {
        axios.get("http://localhost:8080/api/v1/playlist/", {
            params: {id:playlistId}
        }).then(res => {
            console.log(res.data)
        })
    }

    useEffect(() => {
        getPlaylist();
    }, []);

    return (<div>
        <h1>Hello, Playlist № {playlistId}</h1>
    </div>);
}

export default PlaylistPage;