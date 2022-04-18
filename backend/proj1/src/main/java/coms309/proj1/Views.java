package coms309.proj1;

public class Views
{
	public interface Summary {}
	public interface SummaryWithStats extends Summary {}
	public interface SummaryWithFriends extends Summary {}
	public interface SummaryWithFriendsALL extends SummaryWithFriends {}
	public interface Detailed extends Summary{}
	public interface DetailedALL extends Detailed{}
}
