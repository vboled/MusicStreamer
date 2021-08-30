import React, {useEffect, useState} from "react";

function Player(props) {

    const[isAv, setIsAv]=useState(false)

    useEffect(() => {
        if (props.song !== undefined) {
            setIsAv(true)
        }
        setIsAv(false)
    }, [props.song])

    if (isAv)
        return (
            <div>
                <audio></audio>
                <h1>Current Song: {props.song.title}</h1>
            </div>
        )
    return <div>
        <h1>Empty</h1>
    </div>
}

export default Player