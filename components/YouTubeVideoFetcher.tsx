import React, { useState, useEffect } from 'react';

interface YouTubeVideoFetcherProps {
    query: string;
}

const YouTubeVideoFetcher: React.FC<YouTubeVideoFetcherProps> = ({ query }) => {
    const [video, setVideo] = useState<{
        id: string;
        title: string;
        thumbnail: string;
    } | null>(null);



    useEffect(() => {
        const fetchYouTubeVideo = async () => {
            const apiKey = process.env.NEXT_PUBLIC_YOUTUBE_API_KEY;
            if(!apiKey){
                console.error('API key is missing. Set NEXT_PUBLIC_YOUTUBE_API_KEY in your .env file.');
                return;
            }

            const searchQuery = query && query.trim() ? query + ' recipe' : 'default recipe';
            //const searchQuery = 'Chocolate Chip Cookies recipe';

            console.log('Fetching YouTube video for query:', searchQuery);

            try {
                // Build request URL
                const url = `https://www.googleapis.com/youtube/v3/search?part=snippet&q=${encodeURIComponent(
                    searchQuery
                )}&key=${apiKey}&type=video&maxResults=1&regionCode=US`;

                console.log('Request URL:', url);

                // Make API call
                const response = await fetch(url);

                if (!response.ok) {
                    const errorData = await response.json();
                    console.error(`YouTube API responded with status: ${response.status}`, errorData);
                    return;                }

                const data = await response.json();
                console.log('YouTube API response:', data);

                if (data.items && data.items.length > 0) {
                    const videoData = data.items[0];
                    setVideo({
                        id: videoData.id.videoId,
                        title: videoData.snippet.title,
                        thumbnail: videoData.snippet.thumbnails.medium.url,
                    });
                } else {
                    console.warn('No video found for query:', searchQuery);
                    setVideo(null);
                }
            } catch (error) {
                console.error('Error fetching YouTube video:', error);
                setVideo(null);
            }
        };
        fetchYouTubeVideo();
    }, [query]);

    return (
        <div>
            {video ? (
                <div>
                    <h2>Watch Related Recipe Video</h2>
                    <a
                        href={`https://www.youtube.com/watch?v=${video.id}`}
                        target="_blank"
                        rel="noopener noreferrer"
                    >
                        <img src={video.thumbnail} alt={video.title} />
                    </a>
                    <p>{video.title}</p>
                </div>
            ) : (
                <p>No video for this recipe</p>
            )}
        </div>
    );
};

export default YouTubeVideoFetcher;
