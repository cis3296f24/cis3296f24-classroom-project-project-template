import { Link } from "expo-router";
import { Text, View } from "react-native";

export default function Index() {
  return (
    <View className="flex-1 justify-center items-center bg-white">
      <Link
        href="/(tabs)/jaime"
        className="text-blue-600 font-bold text-2xl"
      >
        <Text>Go to Tab View</Text>
      </Link>
    </View>
  );
}
